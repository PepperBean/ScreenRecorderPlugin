module.exports = {
    start: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "_CordovaPlugin", "start", [name]);
    },
    stop: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "_CordovaPlugin", "stop", [name]);
    },
};