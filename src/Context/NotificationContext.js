import {createContext, useEffect, useState} from 'react';

const NotificationContext = createContext();

export const NotificationProvider = ({ children }) => {
    const [notification, setNotification] = useState({});

    useEffect(() => {
        localStorage.setItem('notification', JSON.stringify(notification));
    }, []);

    const updateNotification = (newNotification) => {
        setNotification(newNotification);
    };

    return (
        <NotificationContext.Provider value={{ notification, updateNotification }}>
            {children}
        </NotificationContext.Provider>
    );
};

export default NotificationContext;