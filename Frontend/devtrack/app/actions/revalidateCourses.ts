"use server";

import { revalidateTag } from "next/cache";

export async function revalidateCourses() {
    revalidateTag("courses", "max");
}
