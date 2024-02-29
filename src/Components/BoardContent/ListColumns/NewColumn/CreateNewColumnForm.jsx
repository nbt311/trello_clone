import React from 'react';
import formik, {Formik, useFormik} from "formik";
import * as Yup from "yup";
import {IoMdClose} from "react-icons/io";

const CreateNewColumnForm = ({onSubmit, toggle}) => {
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
                        className='bg-blue-500 hover:bg-blue-600 py-1 px-3 text-white font-semibold rounded-md'>Add list
                </button>
                <IoMdClose className='text-3xl p-1 hover:bg-gray-300 rounded-md' onClick={toggle}/>
            </div>
        </form>
    );
};

export default CreateNewColumnForm;