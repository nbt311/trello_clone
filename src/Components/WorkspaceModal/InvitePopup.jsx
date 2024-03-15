import React, { useState } from 'react';
import {
    Avatar,
    Button, Input,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent, ModalFooter, ModalHeader,
    ModalOverlay, useDisclosure
} from "@chakra-ui/react";
import CopyLinkButton from "./CopyLinkButton";
import { FaLink } from "react-icons/fa6";
import { MdCheckCircleOutline } from "react-icons/md";
import axios from "axios";

const InvitePopup = ({ isOpen, onOpen, onClose, showNotification, setShowNotification,onInputChange }) => {
    const [workspaceEmail, setWorkspaceEmail] = useState('');
    const [suggestedEmails, setSuggestedEmails] = useState([]);
    const hideNotification = () => {
        setShowNotification(true);
    };
    if (showNotification) {
        setTimeout(() => {
            setShowNotification(false);
        }, 3000);
    }

    const handleInputChange = (e) => {
        const query = e.target.value;
        axios.get(`http://localhost:8080/api/users/suggest/${query}`)
            .then(response => {
                setSuggestedEmails(response.data);
            })
            .catch(error => {
                console.error("Error fetching suggested emails:", error);
            });
        setWorkspaceEmail(query);
    };

    const handleEmailClick = (email) => {
        setWorkspaceEmail(email);
    };

    const handlePopupClose = () => {
        setWorkspaceEmail("");
        onClose();
    };

    return (
        <div>
            <Modal size={"xl"} onClose={handlePopupClose} isOpen={isOpen} isCentered>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader></ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <div className="flex flex-col space-y-5">
                            <div className="flex space-x-10">
                                <p className="text-xl font-sans">Invite to Workspace</p>
                                {showNotification &&
                                    <div className="flex space-x-1 mt-1 bg-emerald-200 ">
                                        <MdCheckCircleOutline className="mt-0.5 text-emerald-600" />
                                        <p className="text-sm text-emerald-600">Link copied to clipboard</p>
                                    </div>
                                }
                            </div>
                            <div className="">
                                <Input
                                    value={workspaceEmail}
                                    onChange={handleInputChange}
                                    placeholder="Email address or name"
                                />
                                {workspaceEmail && suggestedEmails.map((user, index) => (
                                    <div
                                        key={index}
                                        style={{cursor: 'pointer'}}
                                        onClick={() => handleEmailClick(user.email)}
                                    >
                                        <div className='flex'>
                                            <Avatar className='mt-1' size='sm' name={user.username} src={user.avatarUrl}/>
                                            <div className='ml-2'>
                                                <p className='text-base font-medium'>{user.username}</p>
                                                <p className='text-sm'>{user.email}</p>
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                            <div className="flex">
                                <div>
                                    <p className="text-l">Invite someone to this Workspace with a link:</p>
                                </div>
                                <div className="flex space-x-1 ml-20 hover:bg-gray-300">
                                    <FaLink className="mt-1" />
                                    <button onClick={hideNotification}><CopyLinkButton /></button>
                                </div>
                            </div>
                        </div>
                    </ModalBody>
                    <ModalFooter></ModalFooter>
                </ModalContent>
            </Modal>
        </div>
    );
};

export default InvitePopup;
