/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.quinhat.dto.AdminUserDTO;
import com.quinhat.exception.UsernameAlreadyExistsException;
import com.quinhat.pojo.User;
import com.quinhat.repositories.UserRepository;
import com.quinhat.services.EmailService;
import com.quinhat.services.UserService;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.quinhat.mapper.AdminUserMapper;
import com.quinhat.utils.Common;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.checkerframework.checker.units.qual.g;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService {
    
    private static final int PASSWORD_LENGTH = 10;
    
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private UserService userDetailsService;
    @Autowired
    private EmailService emailService;
    
    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }
        
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getUserRole()));
        
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }
    
    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        
        String username = params.get("username");

        // Kiểm tra username đã tồn tại chưa
        if (this.userDetailsService.getUserByUsername(username) != null) {
            throw new UsernameAlreadyExistsException("Username đã tồn tại!");
        }
        
        User u = new User();
        
        u.setUsername(params.get("username"));
        u.setFullName(params.get("fullName"));
        u.setEmail(params.get("email"));
        u.setPhone(params.get("phone"));

        // Chuyển đổi birthday từ String sang Date
        String birthdayStr = params.get("birthday");
        if (birthdayStr != null && !birthdayStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date birthday = sdf.parse(birthdayStr);
                u.setBirthday(birthday);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        u.setCreatedAt(new Date());
        u.setIsActive(true);
        u.setPassword(this.passwordEncoder.encode(params.get("password")));
        u.setUserRole(params.get("userRole")); // ✅ sửa lỗi hardcoded "userRole"

        u.setGender(params.get("gender")); // Nếu User có field gender

        // Upload ảnh đại diện nếu có
        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return this.userRepo.addUser(u);
    }
    
    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }

    // Cập nhật user
    @Override
    public User updateUser(String username, Map<String, String> params, MultipartFile avatar) {
        User u = this.getUserByUsername(username);
        
        if (u == null) {
            return null;
        }

        // Only update the fields if they are provided in the params
        if (params.containsKey("fullName")) {
            u.setFullName(params.get("fullName"));
        }
        if (params.containsKey("email")) {
            u.setEmail(params.get("email"));
        }
        if (params.containsKey("phone")) {
            u.setPhone(params.get("phone"));
        }
        if (params.containsKey("gender")) {
            u.setGender(params.get("gender"));
        }

        // Update birthday if provided
        if (params.containsKey("birthday") && !params.get("birthday").isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date birthday = sdf.parse(params.get("birthday"));
                u.setBirthday(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Upload avatar if provided
        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // Save the updated user
        return this.userRepo.updateUser(u);
    }

    //Thay đổi mật khẩu
    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User u = this.getUserByUsername(username);
        
        if (u != null && passwordEncoder.matches(oldPassword, u.getPassword())) {
            u.setPassword(passwordEncoder.encode(newPassword));
            this.userRepo.updateUser(u);
            return true;
        }
        
        return false;
    }

    //Qui
    @Override
    public List<AdminUserDTO> getAllUsers() {
        return userRepo.getAllUsers();
    }
    //Qui

    @Override
    public void save(AdminUserDTO dto) {
        // Nếu password chưa mã hoá, hoặc người dùng nhập lại mới thì mã hoá
        if (dto.getId() == null || Common.isPlainPassword(dto.getPassword())) {
            String password = dto.getPassword();
            dto.setEncryptedPassword(password); // giả sử bên trong setEncryptedPassword() đã mã hoá
        }
        
        if (dto.getAvatar() == null || dto.getAvatar().isEmpty()) {
            dto.setAvatar(null); // Hoặc set URL mặc định nếu muốn
        }
        
        dto.setUserRole(Common.toLowerCase(dto.getUserRole()));
        // Chuyển DTO -> entity User
        User user = AdminUserMapper.toEntity(dto);
        userRepo.save(user);
        
    }
    
    @Override
    public void delete(List<Integer> ids) {
        userRepo.delete(ids);
    }
    
    @Override
    public List<AdminUserDTO> getUsersPaginated(int page, int size) {
        return userRepo.getUsersPaginated(page, size);
    }
    
    @Override
    public long countUsers() {
        return userRepo.countUsers();
    }
    
    @Override
    public User getUserByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }
    
    @Override
    public void updatePassword(User user, String newPassword) {
        // Mã hóa mật khẩu mới trước khi lưu
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        
        userRepo.updatePassword(user, encodedPassword);
    }
    
    @Override
    public boolean resetPasswordAndSendEmail(String username) {
        User user = this.getUserByUsername(username);
        if (user == null) {
            return false;
        }

        // Sinh mật khẩu mới
        String newPassword = generateRandomPassword(10);

        // Cập nhật mật khẩu (đã mã hóa) trong db
        this.updatePassword(user, newPassword);

        // Soạn nội dung email với mật khẩu gốc (chưa mã hóa) để user biết
        String subject = "Mật khẩu mới cho tài khoản của bạn";
        String body = "Xin chào " + user.getUsername() + ",\n\n"
                + "Mật khẩu mới của bạn là: " + newPassword + "\n"
                + "Vui lòng đăng nhập và thay đổi mật khẩu ngay sau khi đăng nhập.\n\n"
                + "Trân trọng,\nĐội ngũ hỗ trợ";
        
        try {
            // Gửi email
            emailService.sendEmail(user.getEmail(), subject, body);
        } catch (Exception ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    @Override
    public AdminUserDTO update(AdminUserDTO dto, MultipartFile newAvatarFile) {
        User existing = userRepo.findById(dto.getId());
        if (existing == null) {
            throw new IllegalArgumentException("User not found");
        }

        // Cập nhật từng trường nếu không null
        if (dto.getFullName() != null) {
            existing.setFullName(dto.getFullName());
        }
        
        if (dto.getEmail() != null) {
            existing.setEmail(Common.toLowerCase(dto.getEmail()));
        }
        
        if (dto.getPhone() != null) {
            existing.setPhone(dto.getPhone());
        }
        
        if (dto.getUsername() != null) {
            existing.setUsername(Common.toLowerCase(dto.getUsername()));
        }
        
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
        if (dto.getUserRole() != null) {
            existing.setUserRole(Common.toLowerCase(dto.getUserRole()));
        }
        
        if (dto.getBirthday() != null) {
            existing.setBirthday(dto.getBirthday());
        }
        
        if (dto.getGender() != null) {
            existing.setGender(Common.toLowerCase(dto.getGender()));
        }
        
        if (dto.getIsActive() != null) {
            existing.setIsActive(dto.getIsActive());
        }

        // Xử lý avatar
        if (newAvatarFile != null && !newAvatarFile.isEmpty()) {
            try {
                // Xóa avatar cũ nếu có
                if (existing.getAvatar() != null && !existing.getAvatar().isBlank()) {
                    String publicId = Common.extractPublicIdFromUrl(existing.getAvatar());
                    if (publicId != null) {
                        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                    }
                }
                // Upload avatar mới
                Map uploadResult = cloudinary.uploader().upload(newAvatarFile.getBytes(), ObjectUtils.emptyMap());
                String avatarUrl = (String) uploadResult.get("url");
                existing.setAvatar(avatarUrl);
            } catch (IOException e) {
                throw new RuntimeException("Upload avatar thất bại: " + e.getMessage());
            }
        }

        // Lưu cập nhật
        userRepo.update(existing);

        // Trả về DTO đã cập nhật
        return AdminUserMapper.toDTO(existing);
    }
    
    @Override
    public User findById(int id) {
        return userRepo.findById(id);
    }
    
}
