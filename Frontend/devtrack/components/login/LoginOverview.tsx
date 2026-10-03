"use client";

import {useState} from "react";
import { useRouter } from "next/navigation";
import { authenticate } from "@/services/UserService";
import type { User } from "@/types";

export default function LoginOverview() {
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("");
    const router = useRouter();

    const handleSubmit = async (e: {preventDefault: () => void}) => {
        e.preventDefault();

        const user: User = { username, password};
        try {
            const loggedInUser = await authenticate(user);
            setTimeout(() => {
                router.push("/")
            }, 1000)
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <div className="form-card panel">
            <div className="page-header">
                <h1>Login</h1>
            </div>
            <form className="form" onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="usernameInput" className="form-label">Username:</label>
                    <input type="text" id="usernameInput" name="username" onChange={(e) => setUsername(e.target.value)} placeholder="Enter username" required/>
                </div>
                <div>
                    <label htmlFor="passwordInput" className="form-label">Password:</label>
                    <input type="password" id="passwordInput" name="password" onChange={(e) => setPassword(e.target.value)} placeholder="Enter password" required/>
                </div>
                <button type="submit" className="form-submit">Login</button>
            </form>
        </div>
    )
}