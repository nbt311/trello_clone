import React, {useState} from 'react';
import {
    Avatar,
    AvatarGroup,
    Box,
    Button, Input,
    Menu,
    MenuButton,
    MenuItem,
    MenuList, Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, Select,
    Tooltip,
    useDisclosure
} from "@chakra-ui/react";
import {CiLock, CiStar} from "react-icons/ci";
import {FaStar} from "react-icons/fa";
import {BiGroup} from "react-icons/bi";
import {MdOutlinePublic} from "react-icons/md";
import {AiOutlineUserAdd} from "react-icons/ai";

const BoardBar = () => {
    const {isOpen, onOpen, onClose} = useDisclosure();
    const [isClicked, setIsClicked] = useState(false);

    const handleClick = () => {
        setIsClicked(!isClicked);
    };
    return (
        <Box sx={{
            width: '100%',
            height: 'auto',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
            gap: 2,
            paddingX: 2,
            overflowX: 'auto',
            borderTop: '1px solid #d1d5db',
        }}>
            <Box sx={{display: 'flex',
                alignItems: 'center',
                gap: 2 ,
                padding: 2,
                borderRadius: 'md',
                backgroundColor: 'gray.100'}}>
                <div className='hover:bg-gray-200 ' onClick={handleClick} style={{ padding: '10px', borderRadius: '5px' }}>
                    {isClicked ? <FaStar /> : <CiStar />}
                </div>
                <Menu>
                        <MenuButton px={2}
                                    py={2}
                                    borderRadius='2'
                                    _hover={{bg: 'gray.200'}}
                                    rounded='md'
                        ><p className='flex'><BiGroup className='mt-1 mr-1'/>Workspace visible</p>
                        </MenuButton>
                    <MenuList className='w-full'>
                        <MenuItem>
                            <div>
                                <p className='flex'><span className="text-red-500"><CiLock className='mt-1 mr-1'/></span>Private</p>
                                <p className='text-sm text-left'>Only board members can see and edit this board.</p>
                            </div>
                        </MenuItem>
                        <MenuItem>
                            <div>
                                <p className='flex'><BiGroup className='mt-1 mr-1'/>Workspace</p>
                                <p className='text-sm text-left'>All members of the name.board Workspace can see and edit this board.</p>
                            </div>
                        </MenuItem>
                        <MenuItem>
                            <div>
                                <p className='flex'><span className="text-green-500"><MdOutlinePublic className='mt-1 mr-1'/></span>Public</p>
                                <p className='text-sm text-left'>Anyone on the internet can see this board. Only board members can edit.</p>
                            </div>
                        </MenuItem>
                    </MenuList>
                </Menu>
            </Box>

            <Box sx={{display: 'flex',
                alignItems: 'center',
                gap: 2 ,
                padding: 2,
                borderRadius: 'md',
                backgroundColor: 'gray.100'}}>
                <AvatarGroup size='sm' max={5}>
                    <Tooltip label="Ryan Florence">
                        <Avatar name='Ryan Florence' src='https://bit.ly/ryan-florence' boxSize="34px"/>
                    </Tooltip>
                    <Tooltip label="Segun Adebayo">
                        <Avatar name='Segun Adebayo' src='https://bit.ly/sage-adebayo' boxSize="34px"/>
                    </Tooltip>
                    <Tooltip label="Prosper Otemuyiwa">
                        <Avatar name='Prosper Otemuyiwa' src='https://bit.ly/prosper-baba' boxSize="34px"/>
                    </Tooltip>
                    <Tooltip label="Christian Nwamba">
                        <Avatar name='Christian Nwamba' src='https://bit.ly/code-beast' boxSize="34px"/>
                    </Tooltip>
                    <Tooltip label="Ryan Florence">
                        <Avatar name='Ryan Florence' src='https://bit.ly/ryan-florence' boxSize="34px"/>
                    </Tooltip>
                    <Tooltip label="Ryan Florence">
                        <Avatar name='Ryan Florence' src='https://bit.ly/ryan-florence' boxSize="34px"/>
                    </Tooltip>
                </AvatarGroup>
                <Button onClick={onOpen}><AiOutlineUserAdd className= 'mr-1'/>Share</Button>
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
            </Box>

        </Box>
    );
};

export default BoardBar;