package ch.bfh.fbi.mobicomp.nfc;

import java.io.IOException;
import java.nio.charset.Charset;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.nfc.R;

public class MainActivity extends Activity {

	private boolean nfcAvailable;
	private NfcAdapter nfcAdapter;
	private PendingIntent pendingIntent;
	private AlertDialog alertDialog;
	private boolean writeNfcEnabled = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Is NFC available on this device?
		nfcAvailable = this.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_NFC);

		if (nfcAvailable) {

			nfcAdapter = NfcAdapter.getDefaultAdapter(this);
			if (nfcAdapter.isEnabled()) {
				// Setting up a pending intent that is invoked when an NFC tag
				// is tapped on the back
				pendingIntent = PendingIntent.getActivity(this, 0, new Intent(
						this, getClass())
						.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
			} else {
				nfcAvailable = false;
			}
		}

		EditText etText = (EditText) findViewById(R.id.text);
		etText.setEnabled(false);

		ToggleButton tbWrite = (ToggleButton) findViewById(R.id.write);
		tbWrite.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				ToggleButton tbWrite = (ToggleButton) findViewById(R.id.write);
				writeNfcEnabled = tbWrite.isChecked();
				EditText etText = (EditText) findViewById(R.id.text);
				if (writeNfcEnabled) {
					etText.setEnabled(true);
				} else {
					etText.setEnabled(false);
				}
			}
		});

	}

	@Override
	public void onNewIntent(Intent intent) {

		if (writeNfcEnabled) {

			// write the tag
			write(intent);

		} else {
			read(intent);
		}

		super.onNewIntent(intent);
	}

	private void read(Intent intent) {
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		Ndef ndef = Ndef.get(tag);

		if (ndef == null) {
			Toast.makeText(this, "Read failed", Toast.LENGTH_LONG).show();
		} else {
			// Read tag
			NdefMessage msg;
			msg = ndef.getCachedNdefMessage();
			if (msg == null || msg.getRecords() == null
					|| msg.getRecords()[0] == null
					|| msg.getRecords()[0].getPayload() == null) {
				Toast.makeText(this, "Read failed", Toast.LENGTH_LONG).show();
				return;
			}

			String text = new String(msg.getRecords()[0].getPayload());

			Toast.makeText(this, text, Toast.LENGTH_LONG).show();

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (nfcAvailable) {
			nfcAdapter.disableForegroundDispatch(this);
		}
	}

	@Override
	protected void onResume() {

		if (nfcAdapter != null && nfcAdapter.isEnabled()) {
			nfcAvailable = true;
		}

		// make sure that this activity is the first one which can handle the
		// NFC tags
		if (nfcAvailable) {
			nfcAdapter.enableForegroundDispatch(this, pendingIntent,
					getNFCIntentFilters(), null);
		}

		super.onResume();
	}

	public static IntentFilter[] getNFCIntentFilters() {
		IntentFilter nfcIntentFilter = new IntentFilter();
		nfcIntentFilter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
		nfcIntentFilter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
		return new IntentFilter[] { nfcIntentFilter };
	}

	private void activateNfC() {
		if (!nfcAdapter.isEnabled()) {

			// if nfc is available but deactivated ask the user whether he
			// wants to enable it. If yes, redirect to the settings.
			alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Enable NFC?");
			alertDialog.setMessage("Enable NFC?");
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							startActivity(new Intent(
									android.provider.Settings.ACTION_WIRELESS_SETTINGS));
						}
					});
			alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "no",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			alertDialog.show();
		} else {
			// display a progress dialog waiting for the NFC tag to be
			// tapped
			writeNfcEnabled = true;
			ProgressDialog writeNfcTagDialog = new ProgressDialog(this);
			writeNfcTagDialog.setMessage("Waiting for NFC tag");
			writeNfcTagDialog.setCancelable(false);
			writeNfcTagDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
					"Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							writeNfcEnabled = false;
							dialog.dismiss();

						}
					});

			writeNfcTagDialog.show();
		}
	}

	/**
	 * Creates a custom MIME type encapsulated in an NDEF record
	 * 
	 * @param mimeType
	 *            The string with the mime type name
	 * @param payload
	 *            Content to write
	 * @return NDEF record
	 */
	public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
		byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
		NdefRecord mimeRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				mimeBytes, new byte[0], payload);
		return mimeRecord;
	}

	/**
	 * Writes an NFC Tag
	 * 
	 * @param tag
	 *            The reference to the tag
	 * @param message
	 *            the message which should be writen on the message
	 * @return true if successful, false otherwise
	 */
	public boolean write(Intent intent) {

		EditText etText = (EditText) findViewById(R.id.text);
		String content = etText.getText().toString();

		// create a new NdefRecord
		NdefRecord record = createMimeRecord("application/ch.bfh.nfcexample",
				content.getBytes());

		// create a new Android Application Record
		NdefRecord aar = NdefRecord.createApplicationRecord(this
				.getPackageName());

		// create a ndef message
		NdefMessage message = new NdefMessage(new NdefRecord[] { record, aar });

		// extract tag from the intent
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Write failed");
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		try {
			// see if tag is already NDEF formatted
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				read(intent);
				ndef.connect();
				if (!ndef.isWritable()) {
					alertDialog.setMessage("Read only tag");
					alertDialog.show();
					return false;
				}

				// work out how much space we need for the data
				int size = message.toByteArray().length;
				if (ndef.getMaxSize() < size) {
					alertDialog.setMessage("Not enough space");
					alertDialog.show();
					return false;
				}

				ndef.writeNdefMessage(message);

			} else {
				// attempt to format tag
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						format.format(message);
					} catch (IOException e) {
						alertDialog
								.setMessage("Unable to format NFC tag to NDEF.");
						alertDialog.show();
						return false;

					}
				} else {
					alertDialog.setMessage("No NDEF support");
					alertDialog.show();
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}

		alertDialog.setTitle("Write success");
		alertDialog.setMessage("Write success");
		alertDialog.show();
		return true;
	}
}
