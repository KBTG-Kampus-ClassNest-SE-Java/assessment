package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.security.Permission;
import com.kbtg.bootcamp.posttest.security.PermissionRepository;
import com.kbtg.bootcamp.posttest.security.Role;
import com.kbtg.bootcamp.posttest.security.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PublicService {

    private final LotteryRepository lotteryRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private PermissionRepository permissionRepository;
    private UserPermissionRepository userPermissionRepository;
    private UserTicketRepository userTicketRepository;



    public PublicService(LotteryRepository lotteryRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, UserPermissionRepository userPermissionRepository, UserTicketRepository userTicketRepository) {
        // In future this will lead to code smell in type of long parameter.
        this.lotteryRepository = lotteryRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userPermissionRepository = userPermissionRepository;
        this.userTicketRepository = userTicketRepository;
    }

    public Map<String, List<String>> getLottery() {
        List<String> lotteries = this.lotteryRepository.findAll().stream().map(Lottery::getTicket).collect(Collectors.toList());

        Map<String, List<String>> allTicket = new HashMap<>();
        allTicket.put("ticket" ,lotteries);
        return allTicket;
    }

    @Transactional
    public String registerUser(UserRegisterDto requestDto) {
        Users user = new Users();

        Optional<Users> checkUser = this.userRepository.findByUsername(requestDto.username);

        if (checkUser.isPresent() ) {
            throw new BadRequestException("username already exits.");
        }


        String maxUserId =this.userRepository.findAll().stream()
                .map(Users::getId)
                .max(Comparator.comparingInt(Integer::parseInt)).orElse("");

        Integer convertIntMaxUserId = Integer.parseInt(maxUserId) + 1;
        String nextUserId = String.format("%010d", convertIntMaxUserId);

        String encodedPassword = this.passwordEncoder.encode(requestDto.password);
        user.setId(nextUserId);
        user.setUsername(requestDto.username);
        user.setPassword(encodedPassword);
        this.userRepository.save(user);

        // Set default role and permission for user.
        int defaultUserRolePermissionValue = 2;
        Optional<Role> roleInDB =  this.roleRepository.findById(Long.valueOf(defaultUserRolePermissionValue));
        String memberRole = roleInDB.map(Role::getRole).orElse("");
        if (memberRole.isEmpty()) {
           throw new BadRequestException("Can't save role for user due to role is empty.") ;
        };

        Optional<Permission> permissionInDB =  this.permissionRepository.findById((long) defaultUserRolePermissionValue);
        String memberPermission = permissionInDB.map(Permission::getPermission).orElse("");
        if (memberPermission.isEmpty()) {
            throw new BadRequestException("Can't save permission for user due to permission is empty.") ;
        };

        Role role = new Role();
        role.setRole(memberRole);
        role.setId(defaultUserRolePermissionValue);

        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUsers(user);

        this.userRoleRepository.save(userRole);

        Permission permission = new Permission();
        permission.setPermission(memberPermission);
        permission.setId(defaultUserRolePermissionValue);

        UserPermission userPermission = new UserPermission();
        userPermission.setPermission(permission);
        userPermission.setUsers(user);

        this.userPermissionRepository.save(userPermission);

        return requestDto.username;
    }


    @Transactional
    public Map<String, Integer> buyLottery(String userId, Integer ticketId) {

        Optional<Users> checkUser = this.userRepository.findById(userId);

        if (checkUser.isEmpty()) {
            throw new NotFoundException("Username not found.");
        }

        Optional<Lottery> checkLottery = this.lotteryRepository.findById((long) ticketId);
        if (checkLottery.isEmpty()) {
            throw new NotFoundException("Ticket not found.");
        }

        Integer ticketAmount = checkLottery.get().getAmount();

        if (ticketAmount <= 0 ) {
            throw new BadRequestException("This ticket_id is out of stock");
        }

        Users user = checkUser.get();
        Lottery lottery =checkLottery.get();
        Integer subTractLotteryAmount =  lottery.getAmount() - 1;
        lottery.setAmount(subTractLotteryAmount);
        this.lotteryRepository.save(lottery);

        UserTicket userTicket = new UserTicket();
        userTicket.setUsers(user);
        userTicket.setLottery(lottery);

        this.userTicketRepository.save(userTicket);

        return Map.of("id", userTicket.getId());


    }
}
