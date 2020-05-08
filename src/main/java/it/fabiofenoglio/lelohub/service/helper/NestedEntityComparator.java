/*
 * Copyright 2001-2018 Fabio Fenoglio. All Rights Reserved.
 *
 * This software is proprietary information of Fabio Fenoglio.
 * Use is subject to license terms.
 *
 */

package it.fabiofenoglio.lelohub.service.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import it.fabiofenoglio.lelohub.domain.proto.HasID;

/**
 *
 */

public abstract class NestedEntityComparator {

	private NestedEntityComparator() {
		// NOP
	}
	
    public static <T1 extends HasID, T2 extends HasID> DifferentListsDifference<T1, T2> compareSets ( Set<T1> list1, Set<T2> list2 ) {
        return compareLists ( new LinkedList<> ( list1 ), new LinkedList<> ( list2 ) );
    }

    public static <T1 extends HasID, T2 extends HasID> DifferentListsDifference<T1, T2> compareLists ( List<T1> list1, List<T2> list2 ) {
    	BiPredicate<T1, T2> matcher = (T1 o1, T2 o2) -> {
    		return o1.getId() != null && o2.getId() != null && o1.getId().equals(o2.getId());
    	};
    	
        DifferentListsDifference<T1, T2> output = new DifferentListsDifference<> ();

        if ( list1 == null && list2 == null ) {

        } else if ( list1 != null && list2 == null ) {

            for ( T1 o: list1 ) {
                output.getElementsInFirstNotInSecond ().add ( o );
            }

        } else if ( list1 == null && list2 != null ) {

            for ( T2 o: list2 ) {
                output.getElementsInSecondNotInFirst ().add ( o );
            }

        } else {
            for ( int i1 = 0; i1 < list1.size (); i1++ ) {
                T1 el1 = list1.get ( i1 );
                T2 el2 = null;
                boolean found = false;
                for ( int i2 = 0; i2 < list2.size (); i2++ ) {
                    el2 = list2.get ( i2 );
                    if ( matcher.test ( el1, el2 ) ) {
                        found = true;
                        break;
                    }
                }
                if ( found ) {
                    output.getElementsInBothFromFirst ().add ( el1 );
                    output.getElementsInBothFromSecond ().add ( el2 );
                    output.getElementsInBoth ().add ( new Couple<> ( el1, el2 ) );
                } else {
                    output.getElementsInFirstNotInSecond ().add ( el1 );
                }
            }

            for ( int i2 = 0; i2 < list2.size (); i2++ ) {
                T2 el2 = list2.get ( i2 );
                T1 el1 = null;
                boolean found = false;
                for ( int i1 = 0; i1 < list1.size (); i1++ ) {
                    el1 = list1.get ( i1 );
                    if ( matcher.test ( el1, el2 ) ) {
                        found = true;
                        break;
                    }
                }
                if ( !found ) {
                    output.getElementsInSecondNotInFirst ().add ( el2 );
                }
            }
        }

        return output;
    }

    public static class DifferentListsDifference<T1 extends HasID, T2 extends HasID> {

        private List<T1> inFirstNotInSecond;

        private List<T2> inSecondNotInFirst;

        private List<T1> inBothFromFirst;

        private List<T2> inBothFromSecond;

        private List<Couple<T1, T2>> inBoth;

        public DifferentListsDifference () {
            inFirstNotInSecond = new LinkedList<> ();
            inSecondNotInFirst = new LinkedList<> ();
            inBothFromFirst = new LinkedList<> ();
            inBothFromSecond = new LinkedList<> ();
            inBoth = new LinkedList<> ();
        }

        public DifferentListsDifference<T1, T2> onElementsInFirstNotInSecond ( Consumer<T1> consumer ) {
            return this.onElementsInFirstNotInSecond ( consumer, null );
        }

        public DifferentListsDifference<T1, T2> onElementsInFirstNotInSecondWBC ( Consumer<T1> consumer, Supplier<Boolean> breakCondition ) {
            return this.onElementsInFirstNotInSecond ( consumer, ( o1 ) -> {
                return breakCondition != null && breakCondition.get ();
            } );
        }

        public DifferentListsDifference<T1, T2> onElementsInFirstNotInSecond ( Consumer<T1> consumer, Predicate<T1> breakCondition ) {
            for ( T1 e: inFirstNotInSecond ) {
                if ( breakCondition != null && breakCondition.test ( e ) ) {
                    break;
                }
                consumer.accept ( e );
                if ( breakCondition != null && breakCondition.test ( e ) ) {
                    break;
                }
            }
            return this;
        }

        public DifferentListsDifference<T1, T2> onElementsInSecondNotInFirst ( Consumer<T2> consumer ) {
            return this.onElementsInSecondNotInFirst ( consumer, null );
        }

        public DifferentListsDifference<T1, T2> onElementsInSecondNotInFirstWBC ( Consumer<T2> consumer, Supplier<Boolean> breakCondition ) {
            return this.onElementsInSecondNotInFirst ( consumer, ( o1 ) -> {
                return breakCondition != null && breakCondition.get ();
            } );
        }

        public DifferentListsDifference<T1, T2> onElementsInSecondNotInFirst ( Consumer<T2> consumer, Predicate<T2> breakCondition ) {
            for ( T2 e: inSecondNotInFirst ) {
                if ( breakCondition != null && breakCondition.test ( e ) ) {
                    break;
                }
                consumer.accept ( e );
                if ( breakCondition != null && breakCondition.test ( e ) ) {
                    break;
                }
            }
            return this;
        }

        public DifferentListsDifference<T1, T2> onElementsInBothFromFirst ( Consumer<T1> consumer ) {
            return this.onElementsInBothFromFirst ( consumer, null );
        }

        public DifferentListsDifference<T1, T2> onElementsInBothFromFirstWBC ( Consumer<T1> consumer, Supplier<Boolean> breakCondition ) {
            return this.onElementsInBothFromFirst ( consumer, ( o1 ) -> {
                return breakCondition != null && breakCondition.get ();
            } );
        }

        public DifferentListsDifference<T1, T2> onElementsInBothFromFirst ( Consumer<T1> consumer, Predicate<T1> breakCondition ) {
            for ( T1 e: inBothFromFirst ) {
                if ( breakCondition != null && breakCondition.test ( e ) ) {
                    break;
                }
                consumer.accept ( e );
                if ( breakCondition != null && breakCondition.test ( e ) ) {
                    break;
                }
            }
            return this;
        }

        public DifferentListsDifference<T1, T2> onElementsInBothFromSecond ( Consumer<T2> consumer ) {
            return this.onElementsInBothFromSecond ( consumer, null );
        }

        public DifferentListsDifference<T1, T2> onElementsInBothFromSecondWBC ( Consumer<T2> consumer, Supplier<Boolean> breakCondition ) {
            return this.onElementsInBothFromSecond ( consumer, ( o1 ) -> {
                return breakCondition != null && breakCondition.get ();
            } );
        }

        public DifferentListsDifference<T1, T2> onElementsInBothFromSecond ( Consumer<T2> consumer, Predicate<T2> breakCondition ) {
            for ( T2 e: inBothFromSecond ) {
                if ( breakCondition != null && breakCondition.test ( e ) ) {
                    break;
                }
                consumer.accept ( e );
                if ( breakCondition != null && breakCondition.test ( e ) ) {
                    break;
                }
            }
            return this;
        }

        public DifferentListsDifference<T1, T2> onElementsInBoth ( BiConsumer<T1, T2> consumer ) {
            return this.onElementsInBoth ( consumer, null );
        }

        public DifferentListsDifference<T1, T2> onElementsInBothWBC ( BiConsumer<T1, T2> consumer, Supplier<Boolean> breakCondition ) {
            return this.onElementsInBoth ( consumer, ( o1, o2 ) -> {
                return breakCondition != null && breakCondition.get ();
            } );
        }

        public DifferentListsDifference<T1, T2> onElementsInBoth ( BiConsumer<T1, T2> consumer, BiPredicate<T1, T2> breakCondition ) {
            for ( Couple<T1, T2> e: inBoth ) {
                if ( breakCondition != null && breakCondition.test ( e.getFirst (), e.getSecond () ) ) {
                    break;
                }
                consumer.accept ( e.getFirst (), e.getSecond () );
                if ( breakCondition != null && breakCondition.test ( e.getFirst (), e.getSecond () ) ) {
                    break;
                }
            }
            return this;
        }

        public List<T1> getElementsInFirstNotInSecond () {
            return inFirstNotInSecond;
        }

        public List<T2> getElementsInSecondNotInFirst () {
            return inSecondNotInFirst;
        }

        public List<T1> getElementsInBothFromFirst () {
            return inBothFromFirst;
        }

        public List<T2> getElementsInBothFromSecond () {
            return inBothFromSecond;
        }

        public List<Couple<T1, T2>> getElementsInBoth () {
            return inBoth;
        }
    }

    public static class Couple<T1 extends HasID, T2 extends HasID> {

        private T1 first;

        private T2 second;

        public Couple ( T1 o1, T2 o2 ) {
            first = o1;
            second = o2;
        }

        public T1 getFirst () {
            return first;
        }

        public T2 getSecond () {
            return second;
        }

    }
}
