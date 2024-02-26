import React from 'react';
import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
    login(form) {
        return axios.post(API_URL + "signin", form)
            .then(response => {
                if (response.data.assessToken) {
                    localStorage.setItem("user", JSON.stringify(response.data));
                }
                return response.data;
            }).catch(error => {
                throw error;
            });
    }

    logout() {
        localStorage.removeItem("user");
    }

    register(username, email, password) {
        return axios.post(API_URL + "signup", {
            username,
            email,
            password
        });
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }
}

export default new AuthService();