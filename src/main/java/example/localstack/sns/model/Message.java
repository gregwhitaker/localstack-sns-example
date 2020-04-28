package example.localstack.sns.model;

public class Message {

  private final int number;

  public Message(final int number) {
    this.number = number;
  }

  public int getNumber() {
    return number;
  }
}
