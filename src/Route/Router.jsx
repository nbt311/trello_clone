import React, {useEffect, useState} from 'react';
import {Navigate, Route, Routes, useNavigate} from "react-router-dom";
import HomePage from "../Pages/HomePage/HomePage";
import ManagePage from "../Pages/ManagePage/ManagePage";
import Login from "../Pages/LoginPage/Login";
import Logout from "../Pages/LogoutPage/Logout";
import Signup from "../Pages/SignUpPage/Signup";
import FirebaseImageUpload from "../FirebaseImageUpload/FirebaseImageUpload";
import CreateWorkspace from "../Components/WorkspaceModal/CreateWorkspace";
import InviteFriendWorkspace from "../Components/WorkspaceModal/InviteFriendWorkspace";
import InvitePopup from "../Components/WorkspaceModal/InvitePopup";
import InvitePopupTwo from "../Components/WorkspaceModal/InvitePopupTwo";


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
                <Routes>
                    <Route
                        path='/login'
                        element={<Login isLoggedIn={isLoggedIn} setLoggedIn={setLoggedIn}/>}
                    />
                    <Route path='/' element={<CreateWorkspace/>}/>
                    <Route path='/manage-profile/*' element={<ManagePage/>}/>
                    <Route path='/logout' element={<Logout isLoggedIn={isLoggedIn} setLoggedIn={setLoggedIn}/>}/>
                    <Route path='/signup' element={<Signup/>}/>
                    {/*<Route path='/upload' element={<FirebaseImageUpload/>}/>*/}
                </Routes>
    );
};

export default Router;