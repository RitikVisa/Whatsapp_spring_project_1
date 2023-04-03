package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<String,User> usermap = new HashMap<>();
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private HashMap<Message,Integer> messageIntegerHashMap;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(String name, String mobile) throws Exception{


        if(!usermap.containsKey(mobile)){


            User user =  new User();
            user.setName(name);
            user.setMobile(mobile) ;
            usermap.put(mobile,user);


            return "SUCCESS";


        }
        else{
            throw new Exception("User already exists");
        }



    }

    public Group createGroup(List<User> users){

        Group newGroup = new Group();

        if(users.size()>2){
            customGroupCount = customGroupCount +1;
            String s= new String( "Group "+ customGroupCount);

            newGroup.setName(s);
            newGroup.setNumberOfParticipants(users.size());

        } else if(users.size() == 2){


            newGroup.setName(users.get(1).getName()) ;
            newGroup.setNumberOfParticipants(2);


        }

        groupUserMap.put(newGroup,users);
        adminMap.put(newGroup,users.get(0));

        return newGroup;




    }


    public int createMessage(String content){


        this.messageId += 1;
        Message msg = new Message(messageId,content);

        return msg.getId();

    }
    public int sendMessage(Message message, User sender, Group group) throws Exception{
//        If the mentioned group does not exist, the application will throw an exception.
//        If the c, the application will throw an exception.



        if(groupUserMap.containsKey(group)){


            List<User>  users =  groupUserMap.get(group) ;


            if(users.contains(sender)){


                List<Message> messages ;


                if(groupMessageMap.containsKey(group)){
                    messages = groupMessageMap.get(group);
                }
                else{
                    messages = new ArrayList<>();
                }


                messages.add(message);


                groupMessageMap.put(group,messages) ;


                return messages.size() ;
            }


            else{


                throw new Exception("You are not allowed to send message");
            }




        }


        else{


            throw new Exception("Group does not exist");
        }

    }
    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if(adminMap.containsKey(group)){
            if(!approver.equals(adminMap.get(group))){
                throw new Exception("Approver does not have rights");
            }else {
                adminMap.put(group,user);
                return "SUCCESS";
            }
        }
        throw new Exception("Group does not exist");




    }
    public int removeUser(User user) throws Exception{
        return 0;
    }
    public String findMessage(Date start, Date end, int K) throws Exception{
        return "";
    }
}
