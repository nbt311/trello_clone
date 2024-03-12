import React, {useEffect, useState} from 'react';
import {
    Avatar, AvatarGroup,
    Button, Card, Checkbox, Input, Menu, MenuButton, MenuItem, MenuList,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    ModalOverlay, Textarea, Tooltip, useDisclosure
} from "@chakra-ui/react";
import {PiChalkboardSimple} from "react-icons/pi";
import {HiMenuAlt2} from "react-icons/hi";
import {RxActivityLog} from "react-icons/rx";
import {MdPersonOutline} from "react-icons/md";
import {TiTag} from "react-icons/ti";
import axios from "axios";
import {useParams} from "react-router-dom";
import CardService from "../../Service/CardService";
import CommentService from "../../Service/CommentService";

const CardModal = ({onOpen, onClose, isOpen, toggleVisibility, card, showMembers}) => {
    const [inputValueDescription, setInputValueDescription] = useState('');
    const [inputValueActivity, setInputValueActivity] = useState('');
    const [originalValue, setOriginalValue] = useState('');
    const [isEditingDescription, setIsEditingDescription] = useState(false);
    const [isEditingActivity, setIsEditingActivity] = useState(false);
    const [members, setMembers] = useState([]);
    const {id} = useParams();
    const [user, setUser] = useState({});
    const [isEditingg, setIsEditingg] = useState(false);
    const [editedTitle, setEditedTitle] = useState(card.title);
    const [greenCheckbox, setGreenCheckbox] = useState(false);
    const [yellowCheckbox, setYellowCheckbox] = useState(false);
    const [orangeCheckbox, setOrangeCheckbox] = useState(false);
    const [redCheckbox, setRedCheckbox] = useState(false);
    const [purpleCheckbox, setPurpleCheckbox] = useState(false);
    const [blueCheckbox, setBlueCheckbox] = useState(false);
    const [comments, setComments] = useState([]);


    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user'));
        setUser(user);
    }, []);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/boards/${id}/members`)
            .then(response => {
                setMembers(response.data);
            }).catch(error => {
            console.error('Error fetching members:', error);

        });
        showLabelToCard(card.id);
        showCommented(card.id);
    }, [id, card.id]);
    const showLabelToCard = (cardId) => {
        CardService.showLabelToCard(cardId)
            .then(response => {
                const labelsData = response.data;
                labelsData.forEach(label => {
                    switch (label.color) {
                        case 'green':
                            setGreenCheckbox(true);
                            break;
                        case 'yellow':
                            setYellowCheckbox(true);
                            break;
                        case 'orange':
                            setOrangeCheckbox(true);
                            break;
                        case 'red':
                            setRedCheckbox(true);
                            break;
                        case 'purple':
                            setPurpleCheckbox(true);
                            break;
                        case 'blue':
                            setBlueCheckbox(true);
                            break;
                        default:
                            break;
                    }
                });
            })
            .catch(error => {
                console.error('Error fetching labels:', error);
            });
    };

    const showCommented = () => {
        CardService.showCommentToCard(card.id)
            .then(response => {
                console.log("aaaaaa",response.data);
                setComments(response.data); // Update the state with the fetched comments
            })
            .catch(error => {
                console.error('Error fetching comments:', error);
            });
    }

    const handleCheckboxChange = (color) => {
        let labelId;
        switch (color) {
            case 'green':
                setGreenCheckbox(!greenCheckbox);
                labelId = 1;
                break;
            case 'yellow':
                setYellowCheckbox(!yellowCheckbox);
                labelId = 2;
                break;
            case 'orange':
                setOrangeCheckbox(!orangeCheckbox);
                labelId = 3;
                break;
            case 'red':
                setRedCheckbox(!redCheckbox);
                labelId = 4;
                break;
            case 'purple':
                setPurpleCheckbox(!purpleCheckbox);
                labelId = 5;
                break;
            case 'blue':
                setBlueCheckbox(!blueCheckbox);
                labelId = 6;
                break;
            default:
                break;
        }
        toggleVisibility(color);
        updateCardLabel(labelId);
    };

    const updateCardLabel = (labelId) => {
        CardService.addLabelToCard(card.id, labelId)
    };

    const handleEditClickDescription = () => {
        setOriginalValue(inputValueDescription);
        setIsEditingDescription(true);
    };

    const handleSaveClickDescription = () => {
        setOriginalValue(inputValueDescription);
        setIsEditingDescription(false);
    };

    const handleCancelClickDescription = () => {
        setInputValueDescription(originalValue);
        setIsEditingDescription(false);
    };

    const handleChangeDescription = (event) => {
        setInputValueDescription(event.target.value);
    };

    const handleTextareaClick = () => {
        setIsEditingActivity(true);
    };

    const handleChangeActivity = (event) => {
        setInputValueActivity(event.target.value);
    };

    const handleSaveClickActivity = async () => {
        try {
            const content = inputValueActivity;
            const cardId = card.id;
            const userId = user.id;
            // Gọi hàm createNewComment từ CommentService và truyền các tham số cần thiết
            await CommentService.createNewComment(content, cardId, userId);

            // Sau khi tạo bình luận thành công, thực hiện các hành động khác
            setIsEditingActivity(false);
            setInputValueActivity('');
        } catch (error) {
            // Xử lý lỗi nếu có
            console.error("Error creating comment:", error);
        }
    };

    const handleCancelClickActivity = () => {
        setIsEditingActivity(false);
        setInputValueActivity('');
    };
    const handleEdit = () => {
        setIsEditingg(true);
    };
    const handleSave = () => {
        setIsEditingg(false);
        CardService.changeCardTitle(card.id, editedTitle)
    };

    const handleCancel = () => {
        setEditedTitle(card.title);
        setIsEditingg(false);
    };

    const handleBlur = () => {
        if (isEditingg === false) {
            handleCancel()
        } else {
            handleSave();
        }
    };

    const handleMemberClick = (data) => {
        CardService.addMemberToCard(card.id, data)
    }
    return (
        <div>
            <Modal size={'3xl'} onClose={onClose} isOpen={isOpen} isCentered>
                <ModalOverlay/>
                <ModalContent>
                    <ModalHeader>
                        <div className='flex h-10'>
                            <PiChalkboardSimple className='mt-1'/>
                            {isEditingg ? (
                                <div className='flex flex-row justify-between items-center'>
                                    <Input
                                        value={editedTitle}
                                        onChange={e => setEditedTitle(e.target.value)}
                                        onBlur={handleBlur}
                                        autoFocus
                                    />
                                </div>
                            ) : (
                                <p className='text-lg font-medium ml-2' onClick={handleEdit}>{editedTitle}</p>
                            )}
                        </div>
                    </ModalHeader>
                    <ModalCloseButton/>
                    <ModalBody>
                        <div className="flex justify-between">
                            <div className="w-[70%]">
                                <div className='flex flex-col '>
                                    {showMembers.length > 0 && (
                                        <p className='text-sm'>members</p>
                                    )}
                                    <AvatarGroup className='mt-3' size='xs' max={2}>
                                        {showMembers.map((member) => (
                                            <Tooltip key={member.id} label={member.username}>
                                                <Avatar key={member.id} name={member.name} src={member.avatarUrl}
                                                        boxSize='40px'/>
                                            </Tooltip>
                                        ))}
                                    </AvatarGroup>
                                </div>

                                <div className="flex mt-5">
                                    <HiMenuAlt2 className='mt-1'/>
                                    <p className='font-bold ml-1 mb-5'>Description</p>
                                </div>
                                {isEditingDescription ? (
                                    <Textarea
                                        placeholder='Add a more detailed description...'
                                        rows={10}
                                        value={inputValueDescription}
                                        onChange={handleChangeDescription}
                                    />
                                ) : (
                                    <div onClick={handleEditClickDescription}>
                                        {inputValueDescription ? (
                                            <p className='ml-5'>{inputValueDescription}</p>
                                        ) : (
                                            <Textarea
                                                placeholder='Add a more detailed description...'
                                                rows={3}
                                                value={inputValueDescription}
                                                onChange={handleChangeDescription}
                                            />
                                        )}
                                    </div>
                                )}
                                {isEditingDescription && (
                                    <div className='mt-2'>
                                        <Button colorScheme='blue' onClick={handleSaveClickDescription}>Save</Button>
                                        <Button onClick={handleCancelClickDescription}>Cancel</Button>
                                    </div>
                                )}
                                <div className="flex mt-10">
                                    <RxActivityLog className='mt-1'/>
                                    <p className='font-bold ml-2'>Activity</p>
                                </div>

                                <div className='flex mt-5'>
                                    <div>
                                        <Avatar key={user.id} name={user.username} src={user.avatarUrl}
                                                boxSize='40px'/>
                                    </div>
                                    <div className='w-full ml-2'>
                                        <div className='w-full ml-2'>
                                            {isEditingActivity ? (
                                                <Textarea
                                                    rows={2}
                                                    value={inputValueActivity}
                                                    onChange={handleChangeActivity}
                                                    placeholder='Write a comment...'
                                                />
                                            ) : (
                                                <div onClick={handleTextareaClick}>
                                                    <Textarea
                                                        rows={1}
                                                        value={inputValueActivity}
                                                        onChange={handleChangeActivity}
                                                        placeholder='Write a comment...'
                                                    />
                                                </div>
                                            )}
                                            {isEditingActivity && (
                                                <div className='mt-2'>
                                                    <Button colorScheme='blue' onClick={handleSaveClickActivity}>Save</Button>
                                                    <Button onClick={handleCancelClickActivity}>Cancel</Button>
                                                </div>
                                            )}
                                        </div>
                                    </div>

                                </div>
                                {/*{!isEditingActivity && inputValueActivity && (*/}
                                    <div className='flex flex-col mt-5'>
                                        {comments.map(comment => (
                                            <div key={comment.id} className="flex mt-2">
                                                <Avatar key={comment.userDTO.id} name={comment.userDTO.username} src={comment.userDTO.avatarUrl} boxSize="40px"/>
                                                <div className="w-full ml-2">
                                                    <div className="flex">
                                                        <p className="font-bold">{comment.userDTO.username}</p>
                                                        <p className='ml-2 mt-1 text-xs'>{comment.elapsedTime}</p>
                                                    </div>
                                                    <Card className="h-auto" style={{ lineHeight: "40px" }}>
                                                        <p className="ml-5">{comment.content}</p>
                                                    </Card>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                {/*)}*/}

                            </div>


                            <div className='flex flex-col space-y-2 w-[20%]'>
                                <p>Add to card</p>

                                <Menu>
                                    <MenuButton borderRadius='md'
                                                bg='gray.200'
                                                _expanded={{bg: 'gray.200'}}
                                                _hover={{bg: 'gray.300'}}>
                                        <div className='flex'>
                                            <MdPersonOutline className='mt-1 mr-1'/>
                                            <p className='font-semibold'>Members</p>
                                        </div>
                                    </MenuButton>
                                    <MenuList>
                                        <div className='flex flex-col items-center space-y-3'>
                                            <div>
                                                <p className='font-semibold'>Members</p>
                                            </div>
                                            <div className='w-[90%]'>
                                                <Input placeholder='Search members'></Input>
                                            </div>
                                            <div className=' w-[90%] '>
                                                <p>Board members</p>
                                            </div>
                                            {members.map(member => (
                                                <MenuItem key={member.id} minH='48px'
                                                          onClick={() => handleMemberClick(member.username)}>
                                                    <Avatar
                                                        boxSize='2rem'
                                                        borderRadius='full'
                                                        src={member.avatarUrl}
                                                        alt={member.username}
                                                        mr='12px'
                                                    />
                                                    <span>{member.username}</span>
                                                </MenuItem>
                                            ))}
                                        </div>

                                    </MenuList>
                                </Menu>

                                <Menu closeOnSelect={false}>
                                    <MenuButton borderRadius='md'
                                                bg='gray.200'
                                                _expanded={{bg: 'gray.200'}}
                                                _hover={{bg: 'gray.300'}}>
                                        <div className='flex'>
                                            <TiTag className='mt-1 mr-1'/>
                                            <p className='font-semibold'>Labels</p>
                                        </div>
                                    </MenuButton>
                                    <MenuList>
                                        <div className='flex flex-col items-center space-y-3'>
                                            <div>
                                                <p className='font-semibold'>Labels</p>
                                            </div>
                                            <div className='w-[90%]'>
                                                <Input placeholder='Search label...'></Input>
                                            </div>
                                            <div className=' w-[90%] '>
                                                <p>Labels</p>
                                            </div>

                                            <MenuItem minH='40px'>
                                                <Checkbox onChange={() => handleCheckboxChange('green')}
                                                          isChecked={greenCheckbox}></Checkbox>
                                                <div className='w-[90%] h-6 bg-green-500 ml-2'></div>
                                            </MenuItem>


                                            <MenuItem minH='48px'>
                                                <Checkbox onChange={() => handleCheckboxChange('yellow')}
                                                          isChecked={yellowCheckbox}></Checkbox>
                                                <div className='w-[90%] h-6 bg-yellow-500 ml-2'></div>
                                            </MenuItem>

                                            <MenuItem minH='48px'>
                                                <Checkbox onChange={() => handleCheckboxChange('orange')}
                                                          isChecked={orangeCheckbox}></Checkbox>
                                                <div className='w-[90%] h-6 bg-orange-500 ml-2'></div>
                                            </MenuItem>

                                            <MenuItem minH='48px'>
                                                <Checkbox onChange={() => handleCheckboxChange('red')}
                                                          isChecked={redCheckbox}></Checkbox>
                                                <div className='w-[90%] h-6 bg-red-500 ml-2'></div>
                                            </MenuItem>

                                            <MenuItem minH='48px'>
                                                <Checkbox onChange={() => handleCheckboxChange('purple')}
                                                          isChecked={purpleCheckbox}></Checkbox>
                                                <div className='w-[90%] h-6 bg-purple-500 ml-2'></div>
                                            </MenuItem>

                                            <MenuItem minH='48px'>
                                                <Checkbox onChange={() => handleCheckboxChange('blue')}
                                                          isChecked={blueCheckbox}></Checkbox>
                                                <div className='w-[90%] h-6 bg-blue-500 ml-2'></div>
                                            </MenuItem>

                                        </div>
                                    </MenuList>
                                </Menu>

                            </div>
                        </div>

                    </ModalBody>
                    <ModalFooter>
                        <Button onClick={onClose}>Close</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </div>
    );
};

export default CardModal;