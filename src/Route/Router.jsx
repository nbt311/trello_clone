import React from 'react';
import {Route, Routes} from "react-router-dom";
import HomePage from "../Pages/HomePage";
import Sidebar from "../Components/SideBar/Sidebar";

const Router = () => {
    return (
        <div>
            <div>
                <Routes>
                    <Route path='/' element={<HomePage/>}/>
                </Routes>
            </div>
        </div>
    );
};

export default Router;