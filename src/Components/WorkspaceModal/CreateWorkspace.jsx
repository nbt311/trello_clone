import React, {useState} from 'react';
import {
    Button, Input,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalOverlay, Select, Textarea, useDisclosure
} from "@chakra-ui/react";
const CreateWorkspace = () => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const [workspaceName, setWorkspaceName] = useState("");
    const [workspaceType, setWorkspaceType] = useState("");

    const isButtonDisabled = !workspaceName || !workspaceType;

    const handleContinue = () => {
        if (!workspaceName || !workspaceType) {
            console.error("Please fill in all fields.");
        }
        // axios.post("/api/create-workspace", {
        //     name: workspaceName,
        //     type: workspaceType,
        // })
        //     .then(response => {
        //     })
        //     .catch(error => {
        //         console.error("Error creating workspace:", error);
        //     });
    };
    return (
            <>
                <Button onClick={onOpen}>Trigger modal</Button>

                <Modal size={"6xl"} onClose={onClose} isOpen={isOpen} isCentered>
                    <ModalOverlay />
                    <ModalContent>
                        <ModalCloseButton/>
                        <ModalBody>
                            <div className="flex justify-between w-full mx-0">
                                <div className="flex flex-col w-[50%] space-y-8 pl-20 pt-10">
                                    <div className="">
                                        <p className="text-2xl font-bold font-sans">Let's build a Workspace</p>
                                        <p className="text-xl font-sans">Boost your productivity by making it easier for everyone to access boards in one location.</p>
                                    </div>
                                    <div className="">
                                        <p className="text-sm font-bold font-sans">Workspace name</p>
                                        <Input  value={workspaceName}
                                                onChange={(e) => setWorkspaceName(e.target.value)} placeholder="Taco's Co." />
                                        <p className="text-xs font-sans">This is the name of your company, team or organization.</p>
                                    </div>
                                    <div>
                                        <p className="text-sm font-bold font-sans">Workspace type</p>
                                        <Select  value={workspaceType}
                                                 onChange={(e) => setWorkspaceType(e.target.value)} placeholder='Choose...'>
                                            <option value='option1'>Option 1</option>
                                            <option value='option2'>Option 2</option>
                                            <option value='option3'>Option 3</option>
                                        </Select>
                                    </div>
                                    <div>
                                        <p className="text-sm font-bold font-sans">Workspace description </p>
                                        <Textarea placeholder='Our team organizes everything here.' />
                                        <p className="text-xs font-sans">Get your members on board with a few words about your Workspace.</p>
                                    </div>
                                    <div><Button onClick={handleContinue} isLoading={isButtonDisabled} className="w-full" colorScheme='blue' >Continue</Button></div>
                                </div>

                                <div className="w-[40%] mx-auto justify-center  bg-blue-200">
                                    <img className="w-full"  src='https://trello.com/assets/d1f066971350650d3346.svg' />
                                </div>
                            </div>
                        </ModalBody>
                    </ModalContent>
                </Modal>
            </>
        )
};

export default CreateWorkspace;