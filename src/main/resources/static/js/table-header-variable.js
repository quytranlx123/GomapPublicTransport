import { formatDate, formatTime } from './common.js'

export const favoriteRouteHeaders = {
    updateUrl: '/gomap/admin/dashboard/favorite-routes/update',
    fieldMappings: {
        routeId: 2,
        userId: 3
    },
    formatters: {
        userId: val => val != null ? val : 'N/A',
        createdAt: val => formatDate(val, 'yyyy-MM-dd HH:mm:ss')
    }
};


export const notificationHeaders = {
    updateUrl: '/gomap/admin/dashboard/notifications/update',
    fieldMappings: {
        title: 3,
        message: 4,
        messageType: 5,
        createdAt: 6,
    },
    formatters: {
        createdAt: val => formatDate(val, 'yyyy-MM-dd HH:mm:ss'),
    }
};

export const routeHeaders = {
    updateUrl: '/gomap/admin/dashboard/routes/update',
    fieldMappings: {
        name: 2,
        description: 3,
        distance: 4,
        duration: 5,
        isActive: 6,
        createdAt: 7
    },
    formatters: {
        isActive: val => val ? 'Đang hoạt động' : 'Tạm dừng',
        createdAt: val => formatDate(val, 'yyyy-MM-dd HH:mm:ss')
    }
};

export const routeStationHeaders = {
    updateUrl: '/gomap/admin/dashboard/route-stations/update',
    fieldMappings: {
        orderStation: 2,
        distance: 3,
        duration: 4,
        routeId: 5,
        stationId: 6
    },
    formatters: {
        routeId: val => val != null ? val : 'N/A',
        stationId: val => val != null ? val : 'N/A'
    }
};

export const scheduleHeaders = {
    updateUrl: '/gomap/admin/dashboard/schedules/update',
    fieldMappings: {
        departureTime: 3,
        arrivalTime: 4,
        vehicleId: 5
    },
    formatters: {
        departureTime: val => formatTime(val),
        arrivalTime: val => formatTime(val),
        vehicleId: val => val ? val : 'N/A'
    }
};


export const stationHeaders = {
    updateUrl: '/gomap/admin/dashboard/stations/update',
    fieldMappings: {
        name: 2,
        latitude: 3,
        longitude: 4,
        address: 5
    },
    formatters: {
        // Bạn có thể bổ sung formatter nếu cần (ví dụ chuyển đổi số hoặc địa chỉ đặc biệt)
    }
};


export const trafficReportHeaders = {
    updateUrl: '/gomap/admin/dashboard/traffic-reports/update',
    fieldMappings: {
        title: 3,
        address: 4,
        latitude: 5,
        longitude: 6,
        image: 7,
        description: 8,
        verified: 10, // was 10
        userId: 11, // was 11
        type: 12      // was 12
    },
    formatters: {

    }

};


export const userNotificationHeaders = {
    updateUrl: '/gomap/admin/dashboard/user-notifications/update',
    fieldMappings: {
        sendAt: 3,
        isRead: 4,
        notificationId: 5,
        userId: 6
    },

    formatters: {
        sendAt: val => formatDate(val, 'yyyy-MM-dd HH:mm:ss'),
        isRead: val => val ? 'Read' : 'Unread',
        userId: val => val !== null ? val : 'N/A'
    }
};


export const userHeaders = {
    updateUrl: '/gomap/admin/dashboard/users/update',
    fieldMappings: {
        fullName: 3,
        email: 4,
        phone: 5,
        username: 6,
        userRole: 8,
        gender: 9,
        birthday: 10,
        avatar: 11,
        isActive: 12,
        createdAt: 13
    },
    formatters: {
        birthday: val => formatDate(val, 'yyyy-MM-dd'),
        createdAt: val => formatDate(val, 'yyyy-MM-dd HH:mm:ss'),
        isActive: val => val ? 'Đang hoạt động' : 'Tạm dừng'
    }
};

export const vehicleHeaders = {
    updateUrl: '/gomap/admin/dashboard/vehicles/update',
    fieldMappings: {
        licensePlate: 3,
        vehicleType: 4,
        driver: 5,
        capacity: 6,
        latitude: 7,
        longitude: 8,
        status: 9,
        routeId: 11,
        active: 12
    },
    formatters: {
        isActive: val => val ? 'Đang hoạt động' : 'Tạm dừng',
        createdAt: val => formatDate(val, 'yyyy-MM-dd HH:mm:ss')
    }
};



