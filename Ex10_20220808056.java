import java.util.*;
public class Ex10_20220808056 {

}
class User{
    private int oid;
    private String username;
    private String email;
    private Set<User> followers;
    private Set<User> following;
    private Set<Post> likedPosts;
    private Map<User, Queue<Message>> messages;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.oid = this.hashCode();
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
        this.likedPosts = new HashSet<>();
        this.messages = new HashMap<>();
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Set<User> getFollowers() {
        return followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }
    public void message(User recipient, String content) {
        if (!messages.containsKey(recipient)) {
            messages.put(recipient, new LinkedList<>());
            recipient.message(this, "");
        }
        Message newMessage = new Message(this, content);
        messages.get(recipient).add(newMessage);
        recipient.read(this);
    }
   public void read(User user) {
        if (messages.containsKey(user)) {
            Queue<Message> userMessages = messages.get(user);
            System.out.println("Messages from " + user.getUsername() + ":");
            while (!userMessages.isEmpty()) {
                Message message = userMessages.poll();
                System.out.println(message.getSender().getUsername() + ": " + message.getContent());
            }
        } else {
            System.out.println("No messages from " + user.getUsername());
        }
    }
    public void follow(User user) {
        if (following.contains(user)) {
            following.remove(user);
            user.followers.remove(this);
        } else {
            following.add(user);
            user.followers.add(this);
        }
    }

    private Map<User, Queue<Message>> getMessages() {
        return messages;
    }
    public void like(Post post) {
        boolean alreadyLiked = post.likedBy(this);
        if (alreadyLiked) {
            likedPosts.remove(post);
        } else {
            likedPosts.add(post);
        }
    }


    public Post post(String content) {
        Post newPost = new Post(content, this);
        return newPost;
    }
}
class Message{
    private boolean seen;
    private Date dateSent;
    private String content;
    private User sender;

    public boolean isSeen() {
        return seen;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }

    public Message(User sender, String content) {
        this.sender = sender;
        this.content = content;
        this.dateSent = new Date();
        this.seen = false;
    }


    public String read(User reader){
        if (sender !=reader){
            seen=true;
        }
        System.out.println("Sent at: "+ dateSent);
        return content;
    }
    public boolean hasRead() {
        return seen;
    }
}
class Post {
    private Date datePosted;
    private String content;
    private Set<User> likes;
    private Map<User, List<Comment>> comments;
    public Post(String content, User postedBy) {
        this.datePosted = new Date();
        this.content = content;
        this.likes = new HashSet<>();
        this.comments = new HashMap<>();
        this.likes.add(postedBy);
    }
    public boolean likedBy(User user) {
        if (likes.contains(user)) {
            likes.remove(user);
            return false;
        } else {
            likes.add(user);
            return true;
        }
    }

    public boolean commentBy(User user, Comment comment) {
        if (!comments.containsKey(user)) {
            comments.put(user, new ArrayList<>());
        }
        return comments.get(user).add(comment);
    }

    public String getContent() {
        System.out.println("Posted at: " + datePosted);
        return content;
    }
    public Comment getComment(User user, int index) {
        if (comments.containsKey(user) && index >= 0 && index < comments.get(user).size()) {
            return comments.get(user).get(index);
        }
        return null;
    }
    public int getCommentCount() {
        int count = 0;
        for (List<Comment> commentList : comments.values()) {
            count += commentList.size();
        }
        return count;
    }

    public int getCommentCountByUser(User user) {
        if (comments.containsKey(user)) {
            return comments.get(user).size();
        }
        return 0;
    }
}


class Comment extends Post {
    private User commenter;

    public Comment(String content, User commenter) {
        super(content, commenter);
        this.commenter = commenter;
    }

    public User getCommenter() {
        return commenter;
    }
}
class SocialNetwork {
    private static Map<User, List<Post>> postsByUsers = new HashMap<>();

    public static User register(String username, String email) {
        User user = new User(username, email);
        if (!postsByUsers.containsKey(user)) {
            postsByUsers.put(user, new ArrayList<>());
            return user;
        }
        return null;
    }
    public static Post post(User user, String content) {
        if (postsByUsers.containsKey(user)) {
            Post newPost = new Post(content, user);
            postsByUsers.get(user).add(newPost);
            return newPost;
        }
        return null;
    }
    public static Set<Post> getFeed(User user) {
        Set<Post> feed = new HashSet<>();
        Set<User> following = user.getFollowing();
        for (User followedUser : following) {
            List<Post> posts = postsByUsers.get(followedUser);
            if (posts != null) {
                feed.addAll(posts);
            }
        }
        return feed;
    }

    public static Map<User, String> search(String keyword) {
        Map<User, String> resultMap = new HashMap<>();
        for (User user : postsByUsers.keySet()) {
            if (user.getUsername().contains(keyword)) {
                resultMap.put(user, user.getUsername());
            }
        }
        return resultMap;
    }

    public static <K, V> Map<V, Set<K>> reverseMap(Map<K, V> map) {
        Map<V, Set<K>> reversedMap = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (!reversedMap.containsKey(value)) {
                reversedMap.put(value, new HashSet<>());
            }
            reversedMap.get(value).add(key);
        }
        return reversedMap;
    }
}




