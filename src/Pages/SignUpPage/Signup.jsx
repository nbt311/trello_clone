import React, {useState} from 'react';
import {useToast} from "@chakra-ui/react";
import axios from "axios";
import {Formik} from "formik";
import {Link, useNavigate} from "react-router-dom";

const Signup = () => {
    const [form, setForm] = useState({});
    const toast = useToast()
    const regex = {
        email: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
        name: /^[a-zA-Z]{6,}$/,
        password: /^[a-zA-Z0-9!@#\$%\^\&*\)\(+=._-]{6,}$/
    }
    const navigate = useNavigate();
    function handleChange(event){
        setForm({
            ...form, [event.target.name]: event.target.value
        });
    }
    function handleValidate() {
        const errors = {};
        if (!form.email) {
            errors.email = "Email Required";
        } else if (!regex.email.test(form.email)) {
            errors.email = "Invalid Email Address";
            console.log("code")
        }
        if (!form.username) {
            errors.username = "Username Required"
        } else if (!regex.password.test(form.username)) {
            errors.username = "Username must use Lowercase and Uppercase letters and must be at least 6 characters"
        }
        // if(!form.name){
        //     errors.name = "Name Required"
        // } else if (!regex.name.test(form.name)){
        //     errors.name = "Full name must use Lowercase and Uppercase letters and must be at least 6 characters"
        // }
        if (!form.password) {
            errors.password = "Password Required";
        } else if (!regex.password.test(form.password)) {
            errors.password = "Password must be at least 6 characters"
        }
        return errors;
    }
    function handleSubmit() {
        console.log(form)
        axios.post("http://localhost:8080/api/auth/signup", form)
            .then(response => console.log(response))
            .catch(error => console.log(error))
            .then(response => {
                toast({
                    title: 'Register Successful',
                    description: 'You have successfully registered.',
                    status: 'success',
                    duration: 3000,
                    isClosable: true,
                });
                navigate("/login");
            })
            .catch(error => {
                toast({
                    title: 'Register Failed',
                    description: 'Please check your credentials and try again.',
                    status: 'error',
                    duration: 3000,
                    isClosable: true,
                });
            });
    }

    return (
        <div>
            <section className="relative bg-gray-100 h-screen">
                <div className="flex flex-col items-center justify-center h-screen">
                    <div className="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-md
                    xl:p-0 space-y-8">
                        <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-sm">
                            <img className="mx-auto h-10 w-auto"
                                 src="https://upload.wikimedia.org/wikipedia/en/8/8c/Trello_logo.svg" alt="Not loaded"/>
                            <h3 className="mt-2 text-center text-2xl leading-9 tracking-tight
                                text-gray-900">Sign up to continue</h3>
                        </div>

                        <div className="pb-3 sm:mx-auto sm:w-full sm:max-w-sm">
                            <Formik initialValues={form} validate={handleValidate} onSubmit={handleSubmit}>
                                {({errors, handleSubmit}) => (
                                    <form onSubmit={handleSubmit}>
                                        <div className='space-y-5'>
                                            <div className={`custom-input ${
                                                errors.email ? "custom-input-error" : ""
                                            }`}>
                                                <label htmlFor="email" className="text-sm font-medium text-gray-900
                                            block text-left ml-6 mb-2">
                                                    Email address
                                                </label>

                                                <input type="email" id="email" name="email" placeholder="Enter email"
                                                       value={form.email || ""} onChange={handleChange}
                                                       className="hover:bg-gray-200 bg-gray-50 border border-gray-300
                                                   text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600
                                                   w-[90%] p-2.5"/>

                                                <p className="text-sm text-red-500">{errors.email}</p>
                                            </div>

                                            <div className={`custom-input ${
                                                errors.username ? "custom-input-error" : ""
                                            }`}>
                                                <label htmlFor="username" className="text-sm font-medium text-gray-900
                                            block text-left ml-6 mb-2">
                                                    Username
                                                </label>

                                                <input type="username" id="username" name="username"
                                                       placeholder="Enter username"
                                                       value={form.username || ""} onChange={handleChange}
                                                       className="hover:bg-gray-200 bg-gray-50 border border-gray-300
                                                   text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600
                                                   w-[90%] p-2.5"/>

                                                <p className="text-sm text-red-500">{errors.username}</p>
                                            </div>

                                            {/*<div className={`custom-input ${*/}
                                            {/*    errors.name ? "custom-input-error" : ""*/}
                                            {/*}`}>*/}
                                            {/*    <label htmlFor="name" className="text-sm font-medium text-gray-900*/}
                                            {/*            dark:text-white block text-left ml-6 ">Full name</label>*/}
                                            {/*    <input type="name" id="name" name="name"*/}
                                            {/*           placeholder="Enter full name"*/}
                                            {/*           value={form.name || ""} onChange={handleChange}*/}
                                            {/*           className="hover:bg-gray-200 bg-gray-50 border border-gray-300*/}
                                            {/*           text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600*/}
                                            {/*           w-[90%] p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400*/}
                                            {/*           dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"/>*/}
                                            {/*    <p className="text-sm text-red-500">{errors.name}</p>*/}
                                            {/*</div>*/}

                                            <div className={`custom-input ${
                                                errors.password ? "custom-input-error" : ""
                                            }`}>
                                                <label htmlFor="password" className="text-sm font-medium text-gray-900
                                            block text-left ml-6 mb-2">
                                                    Password
                                                </label>

                                                <input type="password" id="password" name="password"
                                                       placeholder="Enter password"
                                                       value={form.password || ""} onChange={handleChange}
                                                       className="hover:bg-gray-200 bg-gray-50 border border-gray-300
                                                   text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600
                                                   w-[90%] p-2.5"/>

                                                <p className="text-sm text-red-500">{errors.password}</p>
                                            </div>
                                        </div>

                                        <div className="text-sm font-light text-gray-500 mt-5 mb-3 ">
                                            <Link to="/login" className="font-medium text-primary-600 hover:underline hover:text-blue-500">
                                                Are you already have an account? Sign in
                                            </Link>
                                        </div>

                                        <div>
                                            <button type="submit" className="flex w-full justify-center rounded-md bg-indigo-600
                                            px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500
                                            focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2
                                            focus-visible:outline-indigo-600 mb-5">
                                                Continue
                                            </button>
                                        </div>
                                    </form>
                                )}
                            </Formik>
                        </div>
                    </div>
                </div>
                <div className="w-[25%] absolute bottom-0 left-0">
                    <img
                        src="https://aid-frontend.prod.atl-paas.net/atlassian-id/front-end/5.0.541/trello-left.4f52d13c.svg"/>
                </div>
                <div className="w-[25%] absolute bottom-0 right-0">
                    <img
                        src="https://aid-frontend.prod.atl-paas.net/atlassian-id/front-end/5.0.541/trello-right.3ee60d6f.svg"/>
                </div>
            </section>
        </div>
    );
};

export default Signup;