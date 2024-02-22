import React, {useState} from 'react';
import {Navigate, Route, Routes, useNavigate} from "react-router-dom";
import HomePage from "../Pages/HomePage/HomePage";
import ManagePage from "../Pages/ManagePage/ManagePage";
import Login from "../Pages/LoginPage/Login";

const Router = () => {
    const [isLoggedIn, setLoggedIn] = useState(false);


    return (
        <div>
            <div>
                <Routes>
                    <Route
                        path='/login'
                        element={<Login/>}
                    />
                    <Route path='/' element={<HomePage/>}/>
                    <Route path='/manage-profile/*' element={<ManagePage/>}/>
                </Routes>
            </div>
        </div>
    );
};

export default Router;