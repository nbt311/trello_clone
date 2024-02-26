import { initializeApp } from "firebase/app";
import { getStorage } from "firebase/storage";

const firebaseConfig = {
    apiKey: "AIzaSyCNvNpbCTocj9LCg9yqUxbxX2tzFyUaEus",
    authDomain: "trelloimageupload.firebaseapp.com",
    projectId: "trelloimageupload",
    storageBucket: "trelloimageupload.appspot.com",
    messagingSenderId: "987392302101",
    appId: "1:987392302101:web:ad320c36cc04f8e346421c"
};

const app = initializeApp(firebaseConfig);
export const imageDb = getStorage(app);