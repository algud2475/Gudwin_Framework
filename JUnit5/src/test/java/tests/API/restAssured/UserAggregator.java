package tests.API.restAssured;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import pojo.User;

public class UserAggregator implements ArgumentsAggregator {
    @Override
    public User aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
        return new User(arguments.getInteger(0),
                arguments.getString(1),
                arguments.getString(2),
                arguments.getString(3),
                arguments.getString(4));
    }
}