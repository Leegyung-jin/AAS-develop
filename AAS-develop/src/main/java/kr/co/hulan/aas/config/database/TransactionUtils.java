package kr.co.hulan.aas.config.database;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;

public class TransactionUtils {
    private static final int TX_METHOD_TIMEOUT = 3;

    public static String readOnlyTransactionAttribute() {
        DefaultTransactionAttribute readOnlyAttribute = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
        readOnlyAttribute.setReadOnly(true);
        readOnlyAttribute.setTimeout(TX_METHOD_TIMEOUT);
        return readOnlyAttribute.toString();
    }

    public static String writeTransactionAttribute() {
        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        rollbackRules.add(new RollbackRuleAttribute(Exception.class));
        RuleBasedTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        writeAttribute.setTimeout(TX_METHOD_TIMEOUT);
        return writeAttribute.toString();
    }

    public static String serializableTransactionAttribute() {
        List<RollbackRuleAttribute> serializableRollbackRules = new ArrayList<>();
        serializableRollbackRules.add(new RollbackRuleAttribute(Exception.class));
        RuleBasedTransactionAttribute serializableAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, serializableRollbackRules);
        serializableAttribute.setTimeout(TX_METHOD_TIMEOUT);
        serializableAttribute.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        return serializableAttribute.toString();
    }
}
