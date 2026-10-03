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

export const authenticate = async (user: User): Promise<User | null> => {
    const response = await fetch(apiUrl("/users/login"), {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
    });

    if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(errorData?.message || `Authentication failed`);
    }

    const text = await response.text();

    if (!text) {
        console.log("Empty response from backend");
        return null;
    }

    const data = JSON.parse(text);

    return data || null;
}