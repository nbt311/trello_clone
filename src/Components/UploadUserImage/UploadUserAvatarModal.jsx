import React, {useEffect, useState} from "react";
import {Button, ButtonGroup, Modal, ModalBody, ModalCloseButton, ModalContent, ModalOverlay} from "@chakra-ui/react";
import {FaPhotoVideo} from "react-icons/fa";
import {GrEmoji} from "react-icons/gr";
import {GoLocation} from "react-icons/go";
import {getDownloadURL, ref, uploadBytes} from "firebase/storage";
import {imageDb} from "../../FirebaseImageUpload/Config";
import {v4} from "uuid";
import axios from "axios";

const UploadUserAvatarModal = ({onClose, isOpen, user, setUser}) => {
    const [isDragOver, setIsDragOver] = useState(false)
    const [file, setFile] = useState()
    const [img, setImg] = useState('')

    const handleDrop = (event) => {
        event.preventDefault()
        const droppedFile = event.dataTransfer.file[0]
        if (droppedFile.type.startsWith('image/')) {
            setFile(droppedFile)
        }
    }

    const handleDragOver = (event) => {
        event.preventDefault()
        event.dataTransfer.dropEffect = "copy"
        setIsDragOver(true)
    }

    const handleDragLeave = () => {
        setIsDragOver(false)
    }

    const handleOnChange = (e) => {
        const img = e.target.files[0]
        if (img && (img.type.startsWith('image/'))) {
            setImg(img)
        } else {
            setImg(null)
            alert('Please sellect an image')
        }
    }

    const handleClose = () => {
        setImg(null)
        onClose()
    }

    const handleClick = () => {
        const imgRef = ref(imageDb, `files/${v4()}`)
        uploadBytes(imgRef,img).then(value => {
            getDownloadURL(value.ref).then(url => {
                // Cập nhật trạng thái user
                const updatedUser = { ...user, avatarUrl: url };
                setUser(updatedUser);

                // Lưu trạng thái mới vào localStorage
                localStorage.setItem('user', JSON.stringify(updatedUser));
                setImg(null)
                onClose();

                axios.post(`http://localhost:8080/api/users/${user.id}/avatar`, {
                        email: user.email,
                        image: url,
                        password: user.password
                    }
                )
                    .then(response => console.log(response.data))
                    .catch(error => console.error(error));
            })

        })
    }

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('user'));
        setUser(storedUser);
    }, []);

    return (
        <div>
            <Modal size={'lg'} onClose={onClose} isOpen={isOpen} isCentered>
                <ModalOverlay/>
                <ModalContent>
                    <div className='flex text-2xl font-semibold mt-5 pl-10 items-center'>
                        <h1>Add profile photo</h1>
                    </div>
                    <ModalBody>
                        <div className='flex flex-col py-5 '>
                            <div className='w-full'>
                                {!img && <div>
                                    <div className='flex flex-col items-center'>
                                        <div onDrop={handleDrop} onDragOver={handleDragOver}
                                             onDragLeave={handleDragLeave}
                                             className='flex flex-col w-fit pb-6 px-10 rounded-full items-center justify-center drag-drop border-2 border-dashed'>
                                            <div>
                                                <img
                                                    src="https://firebasestorage.googleapis.com/v0/b/trelloimageupload.appspot.com/o/data%2F31b17eb4-aaab-4180-80bb-d2ebe84c6a22?alt=media&token=7f96b9aa-849b-405b-a536-009491fc682d"
                                                    className='w-40'
                                                    alt=""/>
                                            </div>

                                            <div className='flex flex-col items-center justify-center text-gray-700'>
                                                <p>Drag and drop your</p>
                                                <p>images here</p>
                                            </div>
                                        </div>

                                        <div className='flex flex-col items-center my-3 space-y-2 justify-center'>
                                            <p className='text-base font-medium'>or</p>

                                            <label htmlFor="file-upload">
                                                <div>
                                                    <input className='file-Input' type="file"
                                                           id='file-upload' accept='image/*'
                                                           onChange={handleOnChange} hidden={true}/>

                                                    <p className='flex bg-gray-100 justify-center w-fit text-sm
                                                    font-semibold rounded-sm px-3 py-2 cursor-pointer hover:bg-gray-200'>
                                                        Upload a photo
                                                    </p>
                                                </div>
                                            </label>
                                        </div>
                                    </div>
                                </div>}
                                {img && <img className='max-h-full' src={URL.createObjectURL(img)} alt=""/>}
                            </div>

                            <div className='mt-28 items-end text-right justify-end'>
                                <ButtonGroup gap='1'>
                                    <Button colorScheme='gray' size='sm' onClick={handleClose}>Cancel</Button>
                                    <Button loadingText='Submitting' size='sm' colorScheme='messenger' onClick={handleClick}>Upload</Button>
                                </ButtonGroup>
                            </div>
                        </div>
                    </ModalBody>
                </ModalContent>
            </Modal>
        </div>
    )
}

export default UploadUserAvatarModal;