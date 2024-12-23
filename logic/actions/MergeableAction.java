package logic.actions;

public interface MergeableAction extends DrawAction {
    boolean canMerge(MergeableAction action);
    MergeableAction merge(MergeableAction action);
}
