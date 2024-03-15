import React, {useContext, useEffect, useState} from 'react';
import {IoAppsSharp, IoFilterSharp} from "react-icons/io5";
import Dropdown from "./Dropdown";
import {TbBellRinging2} from "react-icons/tb";
import {FaRegQuestionCircle} from "react-icons/fa";
import {
    Avatar, Box,
    Button, ButtonGroup, Card, FocusLock,Checkbox,
    FormControl,
    FormLabel, IconButton,
    Input,
    Menu,
    MenuButton,
    MenuDivider,
    MenuGroup,
    MenuItem,
    MenuList,
    Popover,
    PopoverArrow,
    PopoverBody,
    PopoverCloseButton,
    PopoverContent,
    PopoverFooter,
    PopoverHeader,
    PopoverTrigger, Stack, useDisclosure
} from "@chakra-ui/react";
import {Link, useNavigate} from "react-router-dom";
import {RiShareBoxLine} from "react-icons/ri";
import {BiGroup} from "react-icons/bi";
import {BsTrello} from "react-icons/bs";
import {GrAdd} from "react-icons/gr";
import WorkspaceContext from "../../Context/WorkspaceContext";
import UserContext from "../../Context/UserContext";
import CreateBoards from "../CreateBoards/CreateBoards";
import NotificationContext from "../../Context/NotificationContext";


const HomeHeader = ({onOpen, onClose, isOpen, workspacelist, users}) => {
    const [userLogin, setUserLogin] = useState({});
    const {user, updateUser} = useContext(UserContext);
    const [isSmallScreen, setIsSmallScreen] = useState(false);
    const navigate = useNavigate();
    const {workspace, updateWorkspace} = useContext(WorkspaceContext);
    // const {notification,updateNotification} = useContext(NotificationContext);
    // const [storeNotification, setStoreNotification] = useState([])


    // useEffect(() => {
    //     if (notification.username) {
    //         setStoreNotification(prevStored => [...prevStored, notification]);
    //     }


    // }, [notification]);

    useEffect(() => {

        const handleResize = () => {
            setIsSmallScreen(window.innerWidth <= 1200);
        };

        // Gọi hàm handleResize khi kích thước màn hình thay đổi
        window.addEventListener('resize', handleResize);

        // Đảm bảo việc remove event listener khi component unmount
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    useEffect(() => {
        const users = JSON.parse(localStorage.getItem('user'));
        setUserLogin(users);
    }, []);

    const handleCreate = () => {
        onOpen()
    }

    const handleWorkspaceClick = (item) => {
        updateWorkspace(item)
        localStorage.setItem('workspace', JSON.stringify(item));
        navigate(`/workspace/${item.id}`);
    };

    const [showForm, setShowForm] = useState(false);

    const toggleForm = () => {
        setShowForm(!showForm);
    };


    return (
        <div className='bg-white border-gray-200  w-full lg:px-6 py-1'>
            <div className='flex flex-row items-center justify-between mx-auto max-w-screen relative'>
                <div className='flex items-center space-x-2'>
                    <div>
                        <IoAppsSharp className='text-4xl cursor-pointer hover:bg-gray-200 rounded-md p-2'/>
                    </div>

                    <Link className='w-[10%] cursor-pointer hover:bg-gray-200 rounded-md p-2' to='/'>
                        <img
                            src="https://upload.wikimedia.org/wikipedia/en/thumb/8/8c/Trello_logo.svg/1280px-Trello_logo.svg.png"
                            alt=""/>
                    </Link>

                    <div className='flex space-x-4'>
                        <Menu>
                            <MenuButton px={4}
                                        py={2}
                                        _hover={{bg: 'gray.200'}}>
                                <Dropdown title='Workspace'/>
                            </MenuButton>
                            <MenuList>
                                {user.ownedWorkspaces.length > 0 && (
                                    <>
                                        <p className="text-sm flex ml-3">Your Workspaces</p>
                                        {user.ownedWorkspaces.map((item) => (
                                            <MenuItem key={item.id}>
                                                <div className='flex cursor-pointer '
                                                     onClick={() => handleWorkspaceClick(item)}>
                                                    <Avatar size='sm' borderRadius='md' name={item.name} src=''/>
                                                    <p className='text-base font-medium ml-2 mt-1'>{item.name}</p>
                                                </div>
                                            </MenuItem>
                                        ))}
                                    </>
                                )}
                                {user.memberWorkspaces.length > 0 && (
                                    <>
                                        <p className="text-sm flex ml-3">Guest Workspaces</p>
                                        {user.memberWorkspaces.map((item) => (
                                            <MenuItem key={item.id}>
                                                <div className='flex cursor-pointer '
                                                     onClick={() => handleWorkspaceClick(item)}>
                                                    <Avatar size='sm' borderRadius='md' name={item.name} src=''/>
                                                    <p className='text-base font-medium ml-2 mt-1'>{item.name}</p>
                                                </div>

                                            </MenuItem>
                                        ))}
                                    </>
                                )}
                            </MenuList>
                        </Menu>


                        <Dropdown title='Recent'/>
                        <Dropdown title='Starred'/>
                        <Dropdown title='Templates'/>
                    </div>

                    <div>
                        <Menu closeOnSelect={false}>
                            {isSmallScreen ? (
                                <MenuButton as={Button} colorScheme='blue'>
                                    <GrAdd/>
                                </MenuButton>
                            ) : (
                                <MenuButton as={Button} colorScheme='blue'>
                                    Create
                                </MenuButton>
                            )}
                            <MenuList className='w-full'>
                                <MenuItem onClick={toggleForm}>
                                    <div>
                                        <p className='flex'><BsTrello className='mt-1 mr-1'/>Create board</p>
                                        <p className='text-sm text-left'>A board is made up of cards ordered on
                                            lists. Use it <br/> to manage projects, track information, or organize
                                            <br/> anything.</p>
                                    </div>
                                </MenuItem>
                                {showForm && (
                                    <div>
                                        <MenuList minWidth='340px'>
                                            <CreateBoards user={users} workspace={workspacelist}/>
                                        </MenuList>
                                    </div>
                                )}

                                <MenuItem onClick={handleCreate}>
                                    <div>
                                        <p className='flex'><BiGroup className='mt-1 mr-1'/>Create Workspace</p>
                                        <p className='text-sm text-left'>A Workspace is a group of boards and people.
                                            Use <br/> it to organize your company, side hustle,family, or <br/> friends.
                                        </p>
                                    </div>
                                </MenuItem>
                            </MenuList>
                        </Menu>
                    </div>

                </div>

                <div className='flex items-center space-x-2'>
                    <div>
                        <form className="max-w-sm px-1">
                            <div className="relative">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    className="absolute top-0 bottom-0 w-5 h-5 my-auto text-gray-400 left-3"
                                    fill="none"
                                    viewBox="0 0 24 24"
                                    stroke="currentColor"
                                >
                                    <path
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                        strokeWidth={2}
                                        d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                                    />
                                </svg>
                                <input
                                    type="text"
                                    placeholder="Search"
                                    className="w-48 h-9 py-3 pl-10 pr-4 text-gray-500 border rounded-md outline-none bg-gray-50
                                focus:bg-white focus:border-blue-400 focus:border-2 focus:w-96"
                                />
                            </div>
                        </form>
                    </div>

                    <div>
                        <Menu>
                            <MenuButton px={2}
                                        py={2}
                                        borderRadius='2'
                                        _hover={{bg: 'gray.200'}}
                                        rounded='md'
                            ><TbBellRinging2 className='text-3xl cursor-pointer hover:bg-gray-200 rounded-full p-1'
                                             color='gray'/>
                            </MenuButton>
                            <MenuList minWidth='400px'>
                                <div className='flex flex-col items-center border border-gray-100'>
                                    <div className=' ml-3'>
                                        <p className='text-xl font-bold'>Notifications</p>
                                    </div>

                                    <hr className='border-1-slate-500 py-1 w-full mt-4'/>
                                    {/*{storeNotification?.map((data) => (*/}
                                    {/*    <div className='w-[90%]  '>*/}
                                    {/*        <Card background={'blue.100'}>*/}
                                    {/*            <div className='flex'>*/}
                                    {/*                <Avatar className='mt-2 ml-2' size='sm' name={data.username} src={data.userAvatar}/>*/}
                                    {/*                <div className='ml-2 mt-3'>*/}
                                    {/*                    <p className='text-base font-medium'>{data.username}</p>*/}
                                    {/*                </div>*/}
                                    {/*            </div>*/}
                                    {/*            <div className=''>*/}
                                    {/*                <p>{data.notification}</p>*/}
                                    {/*            </div>*/}
                                    {/*        </Card>*/}
                                    {/*    </div>*/}
                                    {/*))}*/}
                                </div>
                            </MenuList>
                        </Menu>
                    </div>

                    <div>
                        <FaRegQuestionCircle className='text-3xl cursor-pointer hover:bg-gray-200 rounded-full p-1'
                                             color='gray'/>
                    </div>


                    <div>
                        <Menu>
                            <MenuButton
                                px={1}
                                py={1}
                                transition='all 0.2s'
                                borderRadius='full'
                                _hover={{bg: 'gray.200'}}
                            >
                                <Avatar size='sm' name={userLogin.username} src={userLogin.avatarUrl}/>
                            </MenuButton>

                            <MenuList className='mt-1.5'>
                                <MenuGroup className='font-medium' title='Account'>
                                    <MenuItem pointerEvents={'none'}>
                                        <Avatar size='sm' name={userLogin.username} src={userLogin.avatarUrl}/>
                                        <div className='ml-2'>
                                            <p className='text-base font-medium'>{userLogin.username}</p>
                                            <p className='text-sm'>{userLogin.email}</p>
                                        </div>
                                    </MenuItem>
                                    <MenuItem>Switch accounts</MenuItem>
                                    <MenuItem>
                                        <Link to='/manage-profile/profile-and-visibility'>
                                            <div className='flex flex-row items-center justify-between'>
                                                <div>
                                                    Manage account
                                                </div>
                                                <div className='text-right ml-14'>
                                                    <RiShareBoxLine className='text-base'/>
                                                </div>
                                            </div>
                                        </Link>
                                    </MenuItem>
                                </MenuGroup>

                                <MenuDivider/>

                                <MenuGroup title='Trello'>
                                    <MenuItem>Profile and visibility</MenuItem>
                                    <MenuItem>Activity</MenuItem>
                                    <MenuItem>Cards</MenuItem>
                                    <MenuItem>Settings</MenuItem>
                                    <MenuItem>Theme</MenuItem>
                                </MenuGroup>

                                <MenuDivider/>

                                <MenuGroup>
                                    <MenuItem>Help</MenuItem>
                                    <MenuItem>Shortcuts</MenuItem>
                                </MenuGroup>

                                <MenuDivider/>

                                <MenuGroup>
                                    <MenuItem>
                                        <Link to='/logout'>
                                            <div>
                                                <p>Log Out</p>
                                            </div>
                                        </Link>
                                    </MenuItem>
                                </MenuGroup>
                            </MenuList>
                        </Menu>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default HomeHeader;