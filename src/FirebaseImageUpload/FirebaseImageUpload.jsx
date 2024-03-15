import React, {useEffect, useState} from 'react';
import { imageDb } from "./Config";
import {parse, v4} from "uuid";
import { getDownloadURL,listAll, ref, uploadBytes } from "firebase/storage";
import axios from "axios";

const FirebaseImageUpload = () => {
    const [img, setImg] = useState('')
    const [user, setUser] = useState({});



    const handleClick = () => {
        const imgRef = ref(imageDb, `data/${v4()}`)
        uploadBytes(imgRef,img).then(value => {
            getDownloadURL(value.ref).then(url => {
                console.log(url, "url")

                // Cập nhật trạng thái user
                const updatedUser = { ...user, avatarUrl: url };
                setUser(updatedUser);

                // Lưu trạng thái mới vào localStorage
                localStorage.setItem('user', JSON.stringify(updatedUser));

                axios.post(`http://localhost:8080/api/users/${user.id}/avatar`, {
                        email: user.email,
                        image: url,
                        password: user.password
                    }
                )
                    .then(response => console.log(response.data))
                    .catch(error => console.error(error));
            })

        })
    }

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('user'));
        setUser(storedUser);
    }, []);


    return (
        <div>
            <input type="file" onChange={(e) => setImg(e.target.files[0])}/>
            <button onClick={handleClick} className='border border-black bg-gray-300'>Upload</button>
            <br/>
            {
                <div>
                    <h1>{user.avatarUrl}</h1>
                    <img src={user.avatarUrl} alt=""/>
                </div>
            }
        </div>
    );
};

export default FirebaseImageUpload;