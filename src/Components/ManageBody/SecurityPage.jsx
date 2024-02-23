import React, {useEffect, useState} from 'react';
import {Button, Input, InputGroup, InputRightElement, useToast} from "@chakra-ui/react";
import {MdOutlineRemoveRedEye, MdRemoveRedEye} from "react-icons/md";
import axios from "axios";
import AuthService from "../../Service/auth.service";

const SecurityPage = () => {
    const [showCurrent, setShowCurrent] = React.useState(false)
    const [showNew, setShowNew] = React.useState(false)
    const [currentPassword, setCurrentPassword] = useState("")
    const [newPassword, setNewPassword] = useState("");

    const toast = useToast()
    const isButtonDisabled = !currentPassword;
    const [user, setUser] = useState({});

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        setUser(user);
    }, []);

    const handleCurrentClick = () => setShowCurrent(!showCurrent)
    const handleNewClick = () => setShowNew(!showNew)
    const handlePasswordChange = () => {
        axios.post("http://localhost:8080/api/user/security", { email: user.email,
            currentPassword,
           newPassword,
        })
            .then(response => {
                toast({
                    title: 'Register Successful',
                    description: 'You have successfully registered.',
                    status: 'success',
                    duration: 3000,
                    isClosable: true,
                });
                setCurrentPassword("");
                setNewPassword("");
            })
            .catch(error => {
                toast({
                    title: 'Register Failed',
                    description: 'Please check your credentials and try again.',
                    status: 'error',
                    duration: 3000,
                    isClosable: true,
                });
            });
    }
    return (
        <div>
            <div className='mt-10'>
                <h1 className='text-2xl font-bold'>Security</h1>
            </div>

            <div className='mt-10 space-y-5'>
                <div>
                    <h2 className='text-lg font-semibold'>Change your password</h2>
                    <text className='text-sm'>
                        When you change your password, we keep you logged in to this device but may
                        log you out from your other devices.
                    </text>
                </div>

                <div>
                    <div>
                        <p className='mb-2 font-semibold text-sm'>Current password <span className='text-sm font-semibold text-red-600'>*</span></p>

                        <InputGroup className='mt-3' size='lg'>
                            <Input value={currentPassword} onChange={(e) =>
                                setCurrentPassword(e.target.value) }
                                type={showCurrent ? 'text' : 'password'}
                                placeholder='Enter current password'
                            />
                            <InputRightElement width='3rem'>
                                    <div className='text-2xl cursor-pointer p-1 hover:bg-gray-200 rounded-md' onClick={handleCurrentClick}>
                                        {showCurrent ? <MdOutlineRemoveRedEye /> : <MdRemoveRedEye />}
                                    </div>
                            </InputRightElement>
                        </InputGroup>
                    </div>
                </div>

                <div>
                    <div>
                        <p className='mb-2 font-semibold text-sm'>New password <span className='text-sm font-semibold text-red-600'>*</span></p>

                        <InputGroup className='mt-3' size='lg'>
                            <Input value={newPassword} onChange={(e) =>
                                setNewPassword(e.target.value) }
                                pr='2 rem'
                                type={showNew ? 'text' : 'password'}
                                placeholder='Enter new password'
                            />
                            <InputRightElement width='3rem'>
                                <div className='text-2xl cursor-pointer p-1 hover:bg-gray-200 rounded-md' onClick={handleNewClick}>
                                    {showNew ? <MdOutlineRemoveRedEye /> : <MdRemoveRedEye />}
                                </div>
                            </InputRightElement>
                        </InputGroup>
                    </div>
                </div>

                <Button type='submit' isDisabled={isButtonDisabled} colorScheme='blue' onClick={handlePasswordChange}>Save changes</Button>
            </div>
        </div>
    );
};

export default SecurityPage;