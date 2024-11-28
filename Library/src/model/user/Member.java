package model.user;

import model.enums.MemberLevel;

public class Member extends User {

    // Variables
    private int totalBooksBorrowed;
    private MemberLevel level;

    public Member(String name, Long id) {
        super(name, id);
        // When new member is initialised they will have no book borrowed previously
        totalBooksBorrowed = 0;
        level = MemberLevel.BEGINNER;
    }

    @Override
    public String getLevel() {
        return level.name();
    }

    @Override
    public void updateLevel() {
        incrementTotalBooksBorrowed();
        if(totalBooksBorrowed >= 60){
            level = MemberLevel.EXPERT;
        } else if (totalBooksBorrowed >= 40) {
            level = MemberLevel.ADVANCED;
        } else if (totalBooksBorrowed >= 20) {
            level = MemberLevel.INTERMEDIATE;
        } else {
            level = MemberLevel.BEGINNER;
        }
    }

    // Update total books borrowed (called when a book is borrowed)
    public void incrementTotalBooksBorrowed() {
        totalBooksBorrowed++;
    }
}
