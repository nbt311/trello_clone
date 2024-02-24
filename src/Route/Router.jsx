import React, {useState} from 'react';
import {Navigate, Route, Routes, useNavigate} from "react-router-dom";
import HomePage from "../Pages/HomePage/HomePage";
import ManagePage from "../Pages/ManagePage/ManagePage";
import Login from "../Pages/LoginPage/Login";
import Logout from "../Pages/LogoutPage/Logout";
import Signup from "../Pages/SignUpPage/Signup";
import Workspace from "../Pages/WorkspacePage/Workspace";

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
                    <Route path='/' element={<Workspace/>}/>
                    <Route path='/manage-profile/*' element={<ManagePage/>}/>
                    <Route path='/logout' element={<Logout/>}/>
                    <Route path='/signup' element={<Signup/>}/>
                </Routes>
            </div>
        </div>
    );
};

export default Router;