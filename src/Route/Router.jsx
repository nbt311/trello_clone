import React, {useState} from 'react';
import {Navigate, Route, Routes, useNavigate} from "react-router-dom";
import HomePage from "../Pages/HomePage/HomePage";
import ManagePage from "../Pages/ManagePage/ManagePage";
import Login from "../Pages/LoginPage/Login";
import CreateWorkspace from "../Components/WorkspaceModal/CreateWorkspace";
import InviteFriendWorkspace from "../Components/WorkspaceModal/InviteFriendWorkspace";
import InvitePopup from "../Components/WorkspaceModal/InvitePopup";
import TestDemo from "../Components/WorkspaceModal/TestDemo";
import InvitePopupTwo from "../Components/WorkspaceModal/InvitePopupTwo";


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
                    <Route path='/' element={<InvitePopupTwo/>}/>
                    <Route path='/manage-profile/*' element={<ManagePage/>}/>
                </Routes>
            </div>
        </div>
    );
};

export default Router;