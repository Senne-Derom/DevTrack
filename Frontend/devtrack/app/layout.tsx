import Header from "@/components/Header";
import "@/styles/globals.css";

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>
        <Header />
        <main className="p-6 min-h-screen flex flex-col items-center">
          {children}
        </main>
      </body>
    </html>
  );
}
