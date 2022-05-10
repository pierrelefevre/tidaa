package booksdbclient.model;

import java.util.ArrayList;

/**
 * List of reviews for books, used to get a better tostring for books table in booksview.
 */
public class ReviewList {
    private final ArrayList<Review> reviews;

    public ReviewList(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public ReviewList() {
        this.reviews = new ArrayList<>();
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void addReviews(ArrayList<Review> reviews) {
        this.reviews.addAll(reviews);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews.clear();
        this.reviews.addAll(reviews);
    }

    @Override
    public String toString() {
        double avg = 0;
        int count = 0;
        for (Review item : this.reviews) {
            avg+=item.getRating();
            count++;
        }
        if(count > 0){
            avg = avg/count;

            String returnStr = "";
            for(int i = 0; i < avg; i++){
                returnStr += "★";
            }
            returnStr += " (" + count + ")";
            return returnStr;
        }else{
            return "No reviews found.";
        }
    }
}
