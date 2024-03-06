import React from 'react';
import {
    Avatar,
    Button, Input,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    ModalOverlay, Select, useDisclosure
} from "@chakra-ui/react";

const ShareBoards = () => {
    const {isOpen, onOpen, onClose} = useDisclosure();
    return (
        <div>
            <Button onClick={onOpen}>Open Modal</Button>

            <Modal size={"xl"} isOpen={isOpen} onClose={onClose} isCentered>
                <ModalOverlay/>
                <ModalContent>
                    <ModalHeader>Share board</ModalHeader>
                    <ModalCloseButton/>
                    <ModalBody>
                        <div className='flex'>
                            <Input placeholder='Email address or name' className='mr-1'/>
                            <div className='w-44 mr-1'>
                                <Select placeholder='Member'>
                                    <option value="">Obsever</option>
                                </Select>
                            </div>

                            <Button colorScheme='blue'>Share</Button>
                        </div>

                        <div className='mt-2'>
                            <div className='flex justify-between items-center'>
                                <div className='flex'>
                                    <Avatar className='mt-1' size='sm' src=''
                                            name=''/>
                                    <div className='ml-2'>
                                        <p className='text-base font-medium'>ducanh</p>
                                        <p className='text-sm'>ducanh</p>
                                    </div>
                                </div>

                                <div className='flex space-x-6 items-center'>
                                    <div>
                                        <Select placeholder='Admin' className=''>
                                            <option value="">Obsever</option>
                                        </Select>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </ModalBody>

                    <ModalFooter>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </div>
    );
};

export default ShareBoards;