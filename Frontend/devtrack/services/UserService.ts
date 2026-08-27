const backend_url = process.env.NEXT_PUBLIC_API_BASE_URL?.trim();

const API_BASE_URL = (
    backend_url &&
    backend_url !== "undefined" &&
    backend_url !== "null"
        ? backend_url
        : "http://localhost:8080"
).replace(/\/$/, "");

const apiUrl = (path: string) => `${API_BASE_URL}${path}`;

export type RegisterUserInput = {
    username: string;
    email: string;
    password: string;
}

export const registerUser = async (user: RegisterUserInput) => {
    const response = await fetch(apiUrl("/users/register"), {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
    });

    return response.json();
}