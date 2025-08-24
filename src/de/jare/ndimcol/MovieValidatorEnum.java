/**
 * <copyright>
 * Copyright (c) 2025, Janusch Rentenatus. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * </copyright>
 */
package de.jare.ndimcol;

/**
 *
 * @author Janusch Rentenatus
 */
public enum MovieValidatorEnum {

    EQUALS(MovieValidatorConst.EQUALS) {
        @Override
        public boolean okay() {
            return true;
        }

        @Override
        public boolean correct() {
            return true;
        }

        @Override
        public boolean lengthDifference() {
            return false;
        }
    },
    DIFFERENT(MovieValidatorConst.DIFFERENT) {
        @Override
        public boolean okay() {
            return false;
        }

        @Override
        public boolean correct() {
            return false;
        }

        @Override
        public boolean lengthDifference() {
            return false;
        }
    },
    ACCEPTABLE(MovieValidatorConst.ACCEPTABLE) {
        @Override
        public boolean okay() {
            return true;
        }

        @Override
        public boolean correct() {
            return false;
        }

        @Override
        public boolean lengthDifference() {
            return false;
        }
    },
    INGNORED(MovieValidatorConst.INGNORED) {
        @Override
        public boolean okay() {
            return true;
        }

        @Override
        public boolean correct() {
            return false;
        }

        @Override
        public boolean lengthDifference() {
            return false;
        }
    },
    LONGER(MovieValidatorConst.LONGER) {
        @Override
        public boolean okay() {
            return true;
        }

        @Override
        public boolean correct() {
            return false;
        }

        @Override
        public boolean lengthDifference() {
            return true;
        }
    },
    SHORTER(MovieValidatorConst.SHORTER) {
        @Override
        public boolean okay() {
            return false;
        }

        @Override
        public boolean correct() {
            return false;
        }

        @Override
        public boolean lengthDifference() {
            return true;
        }
    };

    private final int code;

    MovieValidatorEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public abstract boolean okay();

    public abstract boolean correct();

    public abstract boolean lengthDifference();

    public static MovieValidatorEnum fromCode(int code) {
        for (MovieValidatorEnum value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown validation code: " + code);
    }
}
