import React, {useEffect, useState} from 'react';
import ManageHeader from "../../Components/ManageHeader/ManageHeader";
import {Route, Routes} from "react-router-dom";
import ProfilePage from "../../Components/ManageBody/ProfilePage";
import SecurityPage from "../../Components/ManageBody/SecurityPage";

const ManagePage = () => {
    const [user, setUser] = useState({})

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        setUser(user);
    }, []);
    
    return (
        <div>
            <div className='border border-1-slate-500 py-1'>
                <ManageHeader user={user} setUser={setUser}/>
            </div>

            <div className='mt-3 w-full justify-center items-center'>
                <div className='w-[50%] mx-auto'>
                    <Routes>
                        <Route path='/profile-and-visibility' element={
                            <div className='pl-0 text-left'>
                                <ProfilePage user={user} setUser={setUser}/>
                            </div>
                        }/>

                        <Route path='/security' element={
                            <div className='pl-0 text-left'>
                                <SecurityPage/>
                            </div>
                        }/>
                    </Routes>
                </div>
            </div>
        </div>
    );
};

export default ManagePage;