import React, {useEffect, useState} from 'react';
import {Navigate, Route, Routes, useNavigate} from "react-router-dom";
import HomePage from "../Pages/HomePage/HomePage";
import ManagePage from "../Pages/ManagePage/ManagePage";
import Login from "../Pages/LoginPage/Login";
import Logout from "../Pages/LogoutPage/Logout";
import Signup from "../Pages/SignUpPage/Signup";
import FirebaseImageUpload from "../FirebaseImageUpload/FirebaseImageUpload";
import CreateWorkspaceModal from "../Components/WorkspaceModal/CreateWorkspaceModal";
import BoardContentPage from "../Pages/BoardContentPage/BoardContentPage";
import {mockData} from "../apis/mock-data";
import Workspace from "../Pages/WorkspacePage/Workspace";


const Router = () => {
    // const [isLoggedIn, setLoggedIn] = useState();
    // const navigate = useNavigate()

    // useEffect(() => {
    //     if (isLoggedIn) {
    //         navigate('/')
    //     } else {
    //         navigate('/login')
    //     }
    // }, [isLoggedIn]);

    return (
            <Routes>
                <Route
                    path='/login'
                    element={<Login/>}
                />
                <Route path='/*' element={<HomePage/>}/>
                <Route path="/workspace/:id/*" element={<Workspace/>}/>
                <Route path='/create' element={<CreateWorkspaceModal/>}/>
                <Route path='/manage-profile/*' element={<ManagePage/>}/>
                <Route path='/logout' element={<Logout/>}/>
                <Route path='/signup' element={<Signup/>}/>
                <Route path='/upload' element={<FirebaseImageUpload/>}/>
                <Route path='/content' element={<BoardContentPage board = {mockData?.board}/>}/>
            </Routes>
    );
};

export default Router;