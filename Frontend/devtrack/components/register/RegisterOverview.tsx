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
        <div>
            <h1>Register Overview</h1>
            <form action="/register" onSubmit={onSubmit}>
                <input type="text" name="username" placeholder="Username" required
                       onChange={(event) => {
                           setUsername(event.target.value);
                       }} />
                <input type="email" name="email" placeholder="Email" required
                       onChange={(event) => {
                           setEmail(event.target.value);
                       }} />
                <input type="password" name="password" placeholder="Password" required
                       onChange={(event) => {
                           setPassword(event.target.value);
                       }} />
                <button type="submit">Register</button>
            </form>
        </div>
    )
}