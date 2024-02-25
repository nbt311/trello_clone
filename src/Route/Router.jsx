import React, {useEffect, useState} from 'react';
import {Navigate, Route, Routes, useNavigate} from "react-router-dom";
import HomePage from "../Pages/HomePage/HomePage";
import ManagePage from "../Pages/ManagePage/ManagePage";
import Login from "../Pages/LoginPage/Login";
import Logout from "../Pages/LogoutPage/Logout";
import Signup from "../Pages/SignUpPage/Signup";
import FirebaseImageUpload from "../FirebaseImageUpload/FirebaseImageUpload";

const Router = () => {
    const [isLoggedIn, setLoggedIn] = useState(false);
    const navigate = useNavigate()

    useEffect(() => {
        if (isLoggedIn) {
            navigate('/')
        } else {
            navigate('/login')
        }
    }, [isLoggedIn]);

    return (
        <div>
            <div>
                <Routes>
                    <Route
                        path='/login'
                        element={<Login isLoggedIn={isLoggedIn} setLoggedIn={setLoggedIn}/>}
                    />
                    <Route path='/' element={<HomePage/>}/>
                    <Route path='/manage-profile/*' element={<ManagePage/>}/>
                    <Route path='/logout' element={<Logout isLoggedIn={isLoggedIn} setLoggedIn={setLoggedIn}/>}/>
                    <Route path='/signup' element={<Signup/>}/>
                    {/*<Route path='/upload' element={<FirebaseImageUpload/>}/>*/}
                </Routes>
            </div>
        </div>
    );
};

export default Router;