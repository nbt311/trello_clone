import React from 'react';
import {Route, Routes} from "react-router-dom";
import HomePage from "../Pages/HomePage/HomePage";
import ManagePage from "../Pages/ManagePage/ManagePage";

const Router = () => {
    return (
        <div>
            <div>
                <Routes>
                    <Route path='/' element={<HomePage/>}/>
                    <Route path='/manage-profile/*' element={<ManagePage/>}/>
                </Routes>
            </div>
        </div>
    );
};

export default Router;