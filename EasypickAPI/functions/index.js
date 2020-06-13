const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

if (process.env.NODE_ENV === 'development') {
  firebase.functions().useFunctionsEmulator('http://localhost:5001');
}

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.helloWorld = functions.https.onRequest((request, response) => {
  response.send("Hello from Firebase!");
});

exports.avanzarOrden = functions.https.onRequest((request, response) => {

})

exports.notificate = functions.firestore.document('/orders/{orderId}').onUpdate((change, context) => {
    const oldOrder = change.before.data();
    const updatedOrder = change.after.data();
    const oldEvents = oldOrder.events;
    const updatedEvents = updatedOrder.events;
    const newActiveEvent = updatedEvents.filter(ev => !oldEvents.includes(ev) && ev.status === "ACTIVE")[0];
    console.log("Old order: ", oldOrder);
    console.log("New order: ", updatedOrder);
    console.log("User id: ", updatedOrder.payer.id);
    console.log("Event message: ", newActiveEvent.message);
    console.log("Order id: ", context.params.orderId);
    var userRef = admin.firestore().collection('users').doc(updatedOrder.payer.id);

    return userRef.get().then(user => {
        const userData = user.data();
        console.log("User email: ", userData.email)
        const userToken = userData.fcmToken;
        console.log("User token: ", userToken)

        const payload = {
            notification: {
                title: "Easypick",
                body: updatedOrder.events[0].message,
            },
            data: {
              ordenId: context.params.orderId
            }
        };

        return admin.messaging().sendToDevice(userToken, payload)
            .then(function (response) {
                console.log("Successfully sent message:", response);
                return true;
            })
            .catch(function (error) {
                console.log("Error sending message:", error);
            });
    })
});
