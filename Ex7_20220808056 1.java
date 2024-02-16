import java.util.*;
//
public class Ex7_20220808056 {
}
//Interfaces
interface Damageable{
    void takeDamage(int damage);
    void takeHealing(int healing);
    boolean isAlive();
}
interface Caster{
    void castSpell(Damageable target);
    void learnSpell(Spell spell);
}
interface Combat extends Damageable{
    void attack(Damageable target);
    void lootWeapon(Weapon weapon);

}
interface Useable{
    int use();
}

//1st class

class Spell implements Useable{
    private int minHeal;
    private int maxHeal;
    private String name;

    public Spell(int minHeal,int maxHeal,String name) {
        this.minHeal = minHeal;
        this.maxHeal=maxHeal;
        this.name=name;
    }

    public int getMinHeal() {
        return minHeal;
    }

    public void setMinHeal(int minHeal) {
        this.minHeal = minHeal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHeal() {
        return maxHeal;
    }

    public void setMaxHeal(int maxHeal) {
        this.maxHeal = maxHeal;
    }
    private int heal(){
        Random random = new Random();
        return random.nextInt(maxHeal - minHeal + 1) + minHeal;
    }
    @Override
    public int use(){
        return heal();
    }
}

//2nd class

class Weapon implements Useable{
    private int minDamage;
    private int maxDamage;
    private String name;

    public Weapon(int minDamage,int maxDamage,String name) {
        this.maxDamage = maxDamage;
        this.minDamage=minDamage;
        this.name=name;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private int attack(){
        Random random=new Random();
        return random.nextInt(maxDamage-minDamage+1)+minDamage;
    }

    @Override
    public int use() {
        return attack();
    }
}

//3rd class

class Attributes{
    private int strength;
    private int intelligence;
    public Attributes(){
        this(3,3);
    }
    public Attributes(int strength, int intelligence) {
        this.strength = strength;
        this.intelligence = intelligence;
    }
    void increaseStrength(int amount){
        strength+=amount;
    }
    void increaseIntelligence(int amount){
        intelligence+=amount;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public String toString(){
        return "Attributes [Strength= "+strength+", intelligence= "+intelligence+"]";
    }
}

//4th class

abstract class Character implements Comparable<Character>{
   // private int intelligence;
    private String name;
    protected int level=1;
    protected Attributes attributes;
    protected int health=10;
    protected int strength;


    public Character(String name,Attributes attributes) {
        this.name=name;
        this.attributes = new Attributes();
    }

    public int getLevel() {
        return level;
    }
    public String getName() {
        return name;
    }

     public abstract void levelUp();

    @Override
    public int compareTo(Character other) {
        return Integer.compare(this.level, other.level);
    }

    @Override
    public String toString(){
        String className =this.getClass().getSimpleName();
        return className +" LvL:"+level+"–"+attributes;
    }
// ihtiyaç belirtilmemiş. Ancak, bu metotlar genellikle bir sınıfın doğru şekilde işlev görebilmesi için uygulanması gereken standart metodlardır
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return level == character.level && Objects.equals(name, character.name) && Objects.equals(attributes, character.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, attributes);
    }


    //yoktu ben ekledim
   // public abstract void attack(Damageable target);
}

//5th class

abstract class PlayableCharacter extends Character implements Damageable{
    private boolean inParty;
    private Party party;
    private Weapon weapon;

    public PlayableCharacter(String name, Attributes attributes) {
        super(name,attributes);
        this.inParty = false;
        this.party = null;
    }


    //@Override
   // public void attack(Damageable target) {int damage = strength + weapon.use();target.takeDamage(damage);}

    public boolean isInParty(){return inParty;}

    public void joinParty(Party party){
        if(inParty) return;
        try{
            party.addCharacter(this);
            inParty=true;
            this.party = party;
        }catch (AlreadyInpartyException e){
            System.out.println("S/he is already in party");
        }catch (PartyLimitReachedException e){
            System.out.println("This party is full.Character can't join to party");
        }
    }

    public void quitParty() throws CharacterIsNotInPartyException {
        if (inParty){
            try {
                party.removeCharacter(this);
                inParty=false;
                party=null;
            }catch (CharacterIsNotInPartyException e){
                System.out.println("Character Is Not In Party");
            }

        }

    }
}

//6th class
abstract class NonPlayableCharacter extends Character{
    public NonPlayableCharacter(Attributes attributes, String name) {
        super( name, new Attributes());
    }
}

//7th class

class Merchant extends NonPlayableCharacter{
   // public Merchant(int level, int health, String name, int strength, int intelligence) {
 //       super(level, health, name, 0,0);}
   public Merchant(String name) {
       super(new Attributes(),name);
   }
    @Override
    public void levelUp() {}
}

//8th class

class Skeleton extends NonPlayableCharacter implements Combat{
    public Skeleton(int level, int health, String name, int strength, int intelligence) {
        super(new Attributes(), name);
    }
    @Override
     public void levelUp() {
        int newLevel = super.getLevel()+1;
        attributes.increaseIntelligence(1);
        attributes.increaseStrength(1);
    }

    @Override
    public void takeDamage(int damage) {
        health-=damage;
        if (health<=0){
            health=0;
        }
    }

    @Override
    public void takeHealing(int healing) {
        health-=healing;
        if (health<=0){
            health=0;
        }
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }
    @Override
    public void attack(Damageable target) {
        int damage=this.attributes.getStrength();
        target.takeDamage(damage);
        }

    @Override
    public void lootWeapon(Weapon weapon) {}
}

//9th class

class Warrior extends PlayableCharacter implements Combat{
    private Useable weapon;

    public Warrior(String name) {
        super(name,new Attributes(4, 2));
        this.health=35;
    }
    @Override
    public void takeDamage(int damage) {
        health-=damage;
        if (health<=0){
            health=0;
        }
    }

    @Override
    public void takeHealing(int healing) {
        health+=healing;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }
//notsure
    @Override
    public void attack(Damageable target) {
        int damage=this.attributes.getStrength();
        target.takeDamage(damage);
    }

    @Override
    public void lootWeapon(Weapon weapon) {
        this.weapon=weapon;
    }

    @Override
    public void levelUp() {
        level=super.getLevel()+1;
        attributes.increaseStrength(2);
        attributes.increaseIntelligence(1);
    }
}

//10th class
class Cleric extends PlayableCharacter implements Caster{
    private Useable spell;
    public Cleric(String name) {
        super(name,new Attributes(2,4));
        this.health=25;
    }

    @Override
    public void takeDamage(int damage) {
        health-=damage;
        if (health<=0){
            health=0;
        }
    }

    @Override
    public void takeHealing(int healing) {
        health+=healing;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void castSpell(Damageable target) {
        if (spell != null) {
            int damage = attributes.getIntelligence()+spell.use();
            target.takeHealing(damage);
        }
    }


    @Override
    public void learnSpell(Spell spell) {
        this.spell=spell;
    }

    @Override
    public void levelUp() {
        level=super.getLevel()+1;
        attributes.increaseStrength(1);
        attributes.increaseIntelligence(2);
    }
}

//11th class
class Paladin extends PlayableCharacter implements Caster{
    private Useable weapon;
    private Useable spell;

    public Paladin(String name) {
        super( name, new Attributes());
        this.health=30;
    }

    @Override
    public void takeDamage(int damage) {
        health-=damage;
        if (health<=0){
            health=0;
        }
    }

    @Override
    public void takeHealing(int healing) {
        health+=healing;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }



    ////////////


    @Override
    public void castSpell(Damageable target) {
      //  target.takeHealing(spell.use()););
        spell.use();
        //target.takeHealing();
    }

    @Override
    public void learnSpell(Spell spell) {
        this.spell = spell;
    }

    @Override
    public void levelUp() {
        if (level % 2 == 1){
        level=super.getLevel()+1;}
        attributes.increaseStrength(2);
        attributes.increaseIntelligence(1);
    }
}

//12th class
class Party{
    private static final int partyLimit=8;
    //private List<Combat> fighters = new ArrayList<>();
    private Set<PlayableCharacter> fighters = new HashSet<>();
    private Set<PlayableCharacter> healers = new HashSet<>();
    //private List<Caster> healers = new ArrayList<>();
    private int mixedCount=0;


    public void addCharacter(PlayableCharacter character) throws PartyLimitReachedException,AlreadyInpartyException {

        if (fighters.contains(character)|| healers.contains(character)) {
            throw new AlreadyInpartyException();
        }
        //>= mi == mi yapılcak

        if (fighters.size()+healers.size()>=partyLimit){
            throw new PartyLimitReachedException();
        }
        if (character instanceof Combat){
            fighters.add(character);
        }else if (character instanceof Caster){
            healers.add(character);
        }
        if (character instanceof Paladin){
            mixedCount++;
        }
}
        public void removeCharacter(PlayableCharacter character) throws CharacterIsNotInPartyException {
        if (!fighters.remove(character) && !healers.remove(character)) {
            throw new CharacterIsNotInPartyException();
        }
        if (character instanceof Paladin) {
            mixedCount--;
        }
    }


    @Override
    public String toString() {
        List<PlayableCharacter> characters = new ArrayList<>();
        characters.addAll(fighters);
        characters.addAll(healers);
        Collections.sort(characters, Comparator.comparingInt(PlayableCharacter::getLevel));
        StringBuilder sb = new StringBuilder();
        for (PlayableCharacter character : characters) {
            sb.append(character.getName()).append(" (Lv. ").append(character.getLevel()).append(")\n");
        }
        return sb.toString();
    }



    public void partyLevelUp() {
        for (PlayableCharacter character : fighters) {
            if (!(character instanceof Paladin)) {
                character.levelUp();
            }
        }

        for (PlayableCharacter character : healers) {
            if (!(character instanceof Paladin)) {
                character.levelUp();
            }
        }

        for (PlayableCharacter character : fighters) {
            if (character instanceof Paladin) {
                character.levelUp();
            }
        }

        for (PlayableCharacter character : healers) {
            if (character instanceof Paladin) {
                character.levelUp();
            }
        }

    }
}

//13th class

class Barrel implements Damageable{
    private int health=30;
    private int capacity=10;
    public void explode(){
        System.out.println("Explodes");
    }
    public void repair(){
        System.out.println("Repairing");
    }
    @Override
    public void takeDamage(int damage) {
        health-=damage;
        if (health<=0){
            explode();
        }
    }

    @Override
    public void takeHealing(int healing) {
        health = Math.min(health + healing, 30);
        repair();
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }
}
//14th class
class TrainingDummy implements Damageable{
    private int health=25;

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if (health<=0) health=0;
    }

    @Override
    public void takeHealing(int healing) {
        health = Math.min(health + healing, 25);
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }
}
//exceptions

 class PartyLimitReachedException extends Exception {
    public PartyLimitReachedException() {
        super("Oops party limit has been reached.");
    }
}
 class AlreadyInpartyException extends Exception{
    public AlreadyInpartyException(){
        super("Oops character is already in party. ");
    }
 }
 class CharacterIsNotInPartyException extends Exception{
    public CharacterIsNotInPartyException(){
        super("Character is not in party! ");
    }
 }

