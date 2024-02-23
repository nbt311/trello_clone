import React, {useState} from 'react';
import {
    Button, Input,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent, ModalFooter, ModalHeader,
    ModalOverlay, Textarea, useDisclosure
} from "@chakra-ui/react";
import CopyLinkButton from "./CopyLinkButton";
import { FaLink } from "react-icons/fa6";
import { MdCheckCircleOutline } from "react-icons/md";

const InvitePopupTwo = () => {
    const {isOpen, onOpen, onClose} = useDisclosure();
    const [showNotification, setShowNotification] = useState(true);
    const hideNotification = () => {
        setShowNotification(true);
    };
    if (showNotification) {
        setTimeout(() => {
            setShowNotification(false);
        }, 3000);
    }
    const handleCopyLink = () => {
        console.error("Please fill in all fields.");
        // axios.post("/api/create-workspace", {
        //     name: workspaceEmail
        // })
        //     .then(response => {
        //     })
        //     .catch(error => {
        //         console.error("Error creating workspace:", error);
        //     });
    };
    return (
        <div>
            <Button onClick={onOpen}>Trigger modal</Button>

            <Modal size={"xl"} onClose={onClose} isOpen={isOpen} isCentered>
                <ModalOverlay/>
                <ModalContent>
                    <ModalHeader></ModalHeader>
                    <ModalCloseButton/>
                    <ModalBody>
                        <div className="flex flex-col space-y-5">
                            <div className="flex space-x-10">
                                <p className="text-xl font-sans">Invite to Workspace</p>
                                {showNotification &&
                                    <div className="flex space-x-1 mt-1 bg-emerald-200 ">
                                        <MdCheckCircleOutline className="mt-0.5 text-emerald-600"/>
                                        <p className="text-sm text-emerald-600">Link copied to clipboard</p>
                                    </div>
                                }
                            </div>
                            <div className="flex">
                                <Input placeholder="Email address or name"/>
                                <Button colorScheme='blue'>Send invite</Button>
                            </div>
                            <Textarea placeholder="Join this Trello Workspace to start collaborating with me!"></Textarea>
                            <div className="flex">
                                <div>
                                    <p className="text-l">Invite someone to this  Workspace with a link:</p>
                                </div>
                                <div className="flex space-x-1 ml-20 hover:bg-gray-300">
                                    <FaLink className="mt-1"/>
                                    <button onClick={hideNotification}><CopyLinkButton/></button>
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

export default InvitePopupTwo;
