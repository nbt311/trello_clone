import React, {useState} from 'react';
import {FaRegEye, FaRegEyeSlash} from "react-icons/fa";
import {Formik} from "formik";
import axios from "axios";
import {useToast} from '@chakra-ui/react'
import {Link, useNavigate} from "react-router-dom";

const Login = ({isLoggedIn, setLoggedIn}) => {
    const [passwordVisible, setPasswordVisible] = useState(false);
    const [form, setForm] = useState({});
    const toast = useToast();
    const navigate = useNavigate();

    const REGEX = {
        email: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/
    };
    const PasswordVisibility = () => {
        setPasswordVisible(!passwordVisible);
    };

    const handleChange = (event)  => {
        setForm({
            ...form,
            [event.target.name]: event.target.value
        });
    };

    const handleValidate = () => {
        const errors = {};
        if (!form.email) {
            errors.email = "Please check and re-enter";
        } else if (!REGEX.email.test(form.email)) {
            errors.email = "Invalid email address";
        }if (!form.password) {
                errors.password = "Please check and re-enter";
            }
            return errors;
        }

    const handleSubmit =  () => {
        axios.post('http://localhost:8080/api/auth/signin', form)
            .then(response => {
                if (response.data.accessToken) {
                    localStorage.setItem("userLogin", JSON.stringify(response.data));
                }
                toast({
                    title: 'Login Successful',
                    description: 'You have successfully logged in.',
                    status: 'success',
                    duration: 3000,
                    isClosable: true,
                });
                // setLoggedIn(true)
                navigate('/')
            })
            .catch(error => {
                toast({
                    title: 'Login Failed',
                    description: 'Please check your credentials and try again.',
                    status: 'error',
                    duration: 3000,
                    isClosable: true,
                });
            });
    };


    return (
        <section className="relative bg-gray-100 h-screen">
            <div className="flex flex-col  items-center justify-center h-screen">
                <div
                    className="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-md xl:p-0 space-y-10">
                    <div className="items-center justify-center flex space-x-1 pt-10">
                        <img className="w-[33%] "
                             src="data:image/jpeg;base64,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"/>
                    </div>
                    <h1 className="text-xl  md:text-xl font-medium font-sans">
                        Log in to continue
                    </h1>
                    <Formik initialValues={form}
                            validate={handleValidate}
                            onSubmit={handleSubmit}>
                        {({errors, handleSubmit}) => (
                            <form onSubmit={handleSubmit}>
                                <div className="">
                                    <div className={`items-start ${
                                        errors.email ? "custom-input-error" : ""
                                    }`}>
                                        <label htmlFor="email"
                                               className="ml-3 w-24 block mb-2 text-sm font-medium text-gray-900">
                                            Your email
                                        </label>

                                        <input type="text" name="email" id="email" value={form.email || ""}
                                               onChange={handleChange}
                                               className="hover:bg-gray-200 bg-gray-50 border border-gray-300
                                               text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600
                                               focus:border-primary-600  w-[90%] p-2.5"
                                               placeholder="email or username" required=""/>

                                        <p className="text-red-500">{errors.email}</p>
                                    </div>
                                </div>

                                <div className={`pt-4 relative ${
                                    errors.password ? "custom-input-error" : ""
                                }`}>
                                    <label htmlFor="password"
                                           className="ml-3 w-24 block mb-2 text-sm font-medium text-gray-900">
                                        Password
                                    </label>

                                    <input type={passwordVisible ? "text" : "password"} value={form.password || ""}
                                           onChange={handleChange} name="password" id="password"
                                           placeholder="••••••••"
                                           className="hover:bg-gray-200 bg-gray-50 border border-gray-300
                                               text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600
                                               focus:border-primary-600 w-[90%] p-2.5"
                                           required=""/>
                                    <button type="button" className="absolute inset-y-0 right-0 px-7 py-14"
                                            onClick={PasswordVisibility}>
                                        {passwordVisible ? <FaRegEye/> : <FaRegEyeSlash/>}
                                    </button>
                                    <p className="text-red-500">{errors.password}</p>
                                </div>
                                <div className="pt-3">
                                    <button
                                        type="submit"
                                        className="w-[90%] justify-center rounded-md bg-indigo-600 px-3 py-1.5
                                                text-sm font-semibold leading-6 text-white shadow-sm
                                                hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2
                                                focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                                        Continue
                                    </button>
                                </div>
                            </form>
                        )}
                    </Formik>
                    <p className="text-sm font-light text-gray-500 pb-3">
                        Don’t have an account yet?
                        <Link to="/signup" className="font-medium ml-1 text-primary-600 hover:underline">
                            Sign up
                        </Link>
                    </p>
                </div>
            </div>
            <div className="w-[20%] absolute bottom-0 left-0">
                <img
                    src="https://aid-frontend.prod.atl-paas.net/atlassian-id/front-end/5.0.541/trello-left.4f52d13c.svg"/>
            </div>
            <div className="w-[20%] absolute bottom-0 right-0">
                <img
                    src="https://aid-frontend.prod.atl-paas.net/atlassian-id/front-end/5.0.541/trello-right.3ee60d6f.svg"/>
            </div>
        </section>
    )
};

export default Login;