/*
 * Copyright (c) 2012 Berner Fachhochschule, Switzerland.
 * Bern University of Applied Sciences, Engineering and Information Technology,
 * Research Institute for Security in the Information Society, E-Voting Group,
 * Biel, Switzerland.
 *
 * Project UniVote.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 *
 *
 * The crypto object providing the cryptographic functions for:
 *
 *    - Secret key and verification key generation
 *    - Secret key enc- and decryption
 *    - Signature verification of election data
 *    - Vote encoding and ballot creation (encryption, proofing, signing)
 *
 * Most functions are implemented in two versions: Synchronous and asynchronous.
 * The asynchronous implementation is needed for older, slower browsers; they
 * might be removed ones in the future.
 *
 */

//var wsUri = "ws://" + document.location.host + document.location.pathname + "remoteswitchendpoint";
//alert(document.location.pathname);
var wsUri = "ws://" + document.location.host + "/ch_quantasy_smarthome/remoteswitchendpoint";
//var wsUri = "ws://" + document.location.host + "/smarthome/remoteswitchendpoint";
var webSocket = new WebSocket(wsUri);

var output = document.getElementById("output");
webSocket.onopen = function (evt) {
    onOpen(evt);
};


webSocket.onerror = function (evt) {
    onError(evt);
};

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR</span>');
}

function onOpen() {
    writeToScreen("Connected to " + wsUri);
}


function writeToScreen(message) {
    output.innerHTML += message + "<br>";
}

webSocket.onmessage = function (evt) {
    onMessage(evt);
};

function sendText(json) {
    console.log("sending text: " + json);
    webSocket.send(json);
}

function onMessage(evt) {
    console.log("received: " + evt.data);
    drawImageText(evt.data);
}