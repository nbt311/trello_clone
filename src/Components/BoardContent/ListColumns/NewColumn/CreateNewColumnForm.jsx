import React, {useContext} from 'react';
import formik, {Formik, useFormik} from "formik";
import * as Yup from "yup";
import {IoMdClose} from "react-icons/io";
import ColumnService from "../../../../Service/ColumnService";
import {useToast} from "@chakra-ui/react";
import UserContext from "../../../../Context/UserContext";
import WorkspaceContext from "../../../../Context/WorkspaceContext";
import BoardContext from "../../../../Context/BoardContext";
import {values} from "lodash";
import BoardService from "../../../../Service/BoardService";

const CreateNewColumnForm = ({onSubmit, toggle, columns, setColumns}) => {
    const {user, updateUser} = useContext(UserContext)
    const {workspace, updateWorkspace} = useContext(WorkspaceContext);
    const {board, updateBoard} = useContext(BoardContext);

    const toast = useToast()

    const createNewColumn = async () => {
        try {
            const response = await ColumnService.createNewColumn(user.email, workspace.id, formik.values.title, board.id);

            toast({
                title: 'Create Column Successful',
                description: 'You have successfully created a new column.',
                status: 'success',
                duration: 3000,
            });
            BoardService.getListColumn(board.id).then(response => {
                setColumns(response.data)
            })
            console.log("Create column: ", response.data)
            return response.data;
        } catch (error) {
            console.error('Error creating column', error);
            throw error;
        }
    };

    const handleSubmit = async () => {
        try {
            if (!formik.values.title.trim()) {
                toast.closeAll();
                toast({
                    title: 'Create List Fail',
                    description: 'List title cannot be empty.',
                    status: 'error',
                    duration: 2000,
                });
                return;
            }
            const newColumnList = await createNewColumn();
            updateBoard(newColumnList)
            localStorage.setItem('board', JSON.stringify(newColumnList))
        } catch (error) {
            console.error(error)
        }

    }

    const formik = useFormik({
        initialValues: {
            title: '',
        },
        onSubmit: (values) => {
            onSubmit(values.title);
            formik.resetForm();
        },
    });

    return (
        <form className='w-fit h-fit bg-gray-100 p-2 rounded-md text-left'
              onSubmit={formik.handleSubmit}>
            <input
                className='w-[260px] py-1 m-1 text-black bg-gray-100 placeholder:text-black placeholder:pl-2 placeholder:font-semibold'
                id="title"
                name="title"
                type="text"
                placeholder="Enter list title..."
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
                value={formik.values.title}
            />
            {formik.touched.title && formik.errors.title ? (
                <div>{formik.errors.title}</div>
            ) : null}
            <div className='flex items-center space-x-2 mt-2 pl-2'>
                <button type="submit"
                        className='bg-blue-500 hover:bg-blue-600 py-1 px-3 text-white font-semibold rounded-md'
                        onClick={handleSubmit}>
                    Add list
                </button>
                <IoMdClose className='text-3xl p-1 hover:bg-gray-300 rounded-md' onClick={toggle}/>
            </div>
        </form>
    );
};

export default CreateNewColumnForm;