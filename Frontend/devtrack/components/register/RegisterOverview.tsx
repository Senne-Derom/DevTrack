"use client";

import React from "react";
import {registerUser} from "@/services/UserService";

export default function RegisterOverview() {
    const [username, setUsername] = React.useState("");
    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");

    const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        try {
            await registerUser({
                username: username,
                email: email,
                password: password
            });
        } catch (error) {
            console.error("Registration failed:", error);
        }
    }

    return (
        <div className="form-card panel">
            <div className="page-header">
                <h1>Register</h1>
            </div>
            <form className="form" action="/register" onSubmit={onSubmit}>
                <div className="form-field">
                    <label className="form-label" htmlFor="usernameInput">Username:</label>
                    <input id="usernameInput" type="text" name="username" placeholder="Enter username" required
                           onChange={(event) => {
                               setUsername(event.target.value);
                           }} />
                </div>
                <div className="form-field">
                    <label className="form-label" htmlFor="emailInput">Email:</label>
                    <input id="emailInput" type="email" name="email" placeholder="Enter email" required
                           onChange={(event) => {
                               setEmail(event.target.value);
                           }} />
                </div>
                <div className="form-field">
                    <label className="form-label" htmlFor="passwordInput">Password:</label>
                    <input id="passwordInput" type="password" name="password" placeholder="Enter password" required
                           onChange={(event) => {
                               setPassword(event.target.value);
                           }} />
                </div>
                <button type="submit" className="form-submit">Register</button>
            </form>
        </div>
    )
}