import React from 'react';
import {
    Button, Input,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalOverlay, useDisclosure
} from "@chakra-ui/react";

const InviteFriendWorkspace = () => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return (
        <div>
            <Button onClick={onOpen}>Trigger modal</Button>

            <Modal size={"6xl"} onClose={onClose} isOpen={isOpen} isCentered>
                <ModalOverlay/>
                <ModalContent>
                    <ModalCloseButton/>
                    <ModalBody>
                    <div className="flex">
                            <div className="flex flex-col w-[50%] space-y-8 pl-5 pt-10">
                                <div className="">
                                    <p className="text-2xl font-bold font-sans">Invite your team</p>
                                    <p className="text-xl font-sans">Trello makes teamwork your best work. Invite your
                                        new team members to get going!</p>
                                </div>

                                <div>
                                    <div>
                                        <p className="text-lg font-bold font-sans">Workspace members</p>
                                    </div>
                                    <Input placeholder="e.g. calrissian@cloud.ci"/>
                                    <div className="flex space-x-1">
                                        <p className="font-bold" >Pro tip!</p>
                                        <p>Add multiple emails, or invite them with one link.</p>
                                    </div>
                                </div>

                                <div><Button className="w-full" colorScheme='blue'>Continue</Button></div>
                            </div>
                            <div className="w-[40%] mx-auto justify-center bg-blue-200">
                                <img className="w-full"  src='https://trello.com/assets/d1f066971350650d3346.svg' />
                            </div>
                    </div>
                    </ModalBody>
                </ModalContent>
            </Modal>
        </div>
    );
};

export default InviteFriendWorkspace;
