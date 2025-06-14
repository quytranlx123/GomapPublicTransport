// session-check.js

const originalFetch = window.fetch;

window.fetch = async function (...args) {
    const response = await originalFetch(...args);

    if (response.status === 401) {
        redirectToLogin("session=expired");
        return null;
    }

    if (response.redirected && response.url.includes("/login")) {
        redirectToLoginFromUrl(response.url);
        return null;
    }

    return response;
};


export function setupAxiosInterceptor(axiosInstance) {
    if (!axiosInstance || typeof axiosInstance.interceptors === "undefined")
        return;

    axiosInstance.interceptors.response.use(
            response => response,
            error => {
                if (error.response) {
                    const status = error.response.status;
                    const redirectUrl = error.response.request?.responseURL || "";

                    if (status === 401) {
                        redirectToLogin("session=expired");
                        return Promise.reject(new Error("Unauthorized - Redirecting to login"));
                    }

                    if (status === 302 && redirectUrl.includes("/login")) {
                        redirectToLoginFromUrl(redirectUrl);
                        return Promise.reject(new Error("Redirecting to login"));
                    }
                }

                return Promise.reject(error);
            }
    );
}

function redirectToLogin(query = "") {
    const baseLogin = "/admin/dashboard/login";
    window.location.href = query ? `${baseLogin}?${query}` : baseLogin;
}

function redirectToLoginFromUrl(url) {
    try {
        const redirectUrl = new URL(url);
        if (redirectUrl.pathname.includes("/login")) {
            window.location.href = url;
        }
    } catch (e) {
        // Bỏ qua lỗi URL không hợp lệ
    }
}
