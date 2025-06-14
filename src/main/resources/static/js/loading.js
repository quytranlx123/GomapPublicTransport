export async function showOverlayWithProgress(callback) {
    const overlay = document.getElementById('overlay');
    const progressBar = document.getElementById('progressBar');

    overlay.style.display = 'flex';
    document.body.style.overflow = 'hidden'; // chỉ cần đặt 1 lần

    progressBar.style.width = '0%';
    let progress = 0;

    const interval = setInterval(() => {
        if (progress < 90) {
            progress += 5;
            progressBar.style.width = progress + '%';
        }
    }, 200);

    try {
        await callback();  // Đợi callback thực hiện xong
        clearInterval(interval);
        progressBar.style.width = '100%';
        setTimeout(() => {
            overlay.style.display = 'none';
            document.body.style.overflow = ''; // khôi phục scroll
        }, 500);
    } catch (error) {
        clearInterval(interval);
        overlay.style.display = 'none';
        document.body.style.overflow = ''; // khôi phục scroll
        throw error;
    }
}
