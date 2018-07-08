const functions = require('firebase-functions');
const admin = require('firebase-admin')
admin.initializeApp(functions.config().firebase)

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
exports.tapFunction = functions.database.ref('/taps/{pushId}').onWrite((change,context)=>{
    console.log("Push notification triggered")
    const snapshot = change.after.val();
    const payload = {
        data : {
            title : snapshot.type,
            body : snapshot.message
        }
    };
    const option ={
        priority : "high",
        timeToLive: 60 * 60 * 24
    };
    return admin.messaging().sendToTopic("pushNotification",payload,option);
});
exports.calendarFunction = functions.database.ref('/calendarEvents/{pushId}').onWrite((change,context)=>{
    console.log("Push notification triggered")
    const snapshot = change.after.val();
    const payload={
        data :{
            title : "New event added",
            body : snapshot.title
        }
    };
    const option ={
        priority : "high",
        timeToLive: 60 * 60 * 24
    };
    return admin.messaging().sendToTopic("pushNotification",payload,option);
})