/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.dmr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;

/**
 * Base64 encoding/decoding utilities.
 * 
 * @author Jonathan Pearlin
 */
public class Base64 {

    /**
     * Encoding padding character to fill empty bytes at the end of the sequence.
     */
    private static final char PADDING_CHARACTER = '=';

    /**
     * The byte value of the padding character (i.e. the index not found value from the BASE_64_CHARACTERS string).
     */
    private static final int PADDING_VALUE = -1;

    /**
     * Dictionary of Base64 encoding characters.
     */
    private static final String BASE_64_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    /**
     * Encoding block size of bytes used to encode byte data into Base64.
     */
    private static final int ENCODE_BLOCK_SIZE_BYTES = 3;

    /**
     * Decoding block size of bytes required to decode a Base64 string back into byte data.
     */
    private static final int DECODE_BLOCK_SIZE_BYTES = 4;

    /**
     * End of stream constant.
     */
    private static final int EOS = -1;

    private static int readMostly(java.io.InputStream input, final byte[] buf) throws IOException {
        int offset = 0;
        int remaining = buf.length;
        int read = 0;

        while (remaining > 0) {
            read = input.read(buf, offset, remaining);
            if (read == EOS)
                return offset == 0 ? EOS : offset;

            offset += read;
            remaining -= read;
        }

        return offset;
    }

    /**
     * Encodes the supplied byte array into a Base64 encoded string. Note that this implementation does NOT insert newline
     * characters every 72 bytes.
     * 
     * @param bytes A byte array of data to be encoded.
     * @return The Base64 encoded string representation of the bytes or {@code null} if the data cannot be encoded.
     * @throws IOException if an error occurs during encoding.
     */
    public static final String encode(final byte[] bytes) throws IOException {
        if (bytes != null) {
            return new String(encodeBytes(bytes));
        } else {
            return null;
        }
    }

    /**
     * Decodes a Base64 encoded string into a byte array of the originally encoded values. Note that this implementation does
     * NOT handle decoding additional newline characters added during the encoding process every 72 bytes.
     * 
     * @param base64 A Base64 encoded string that represents binary data.
     * @return A byte array containing the originally encoded binary data or {@code null} if the data cannot be decoded.
     * @throws IOException if an error occurs during decoding.
     */
    public static final byte[] decode(final String base64) throws IOException {
        if (base64 != null) {
            return decodeBytes(base64.getBytes());
        } else {
            return null;
        }
    }

    /**
     * Decodes an array of bytes that represents Base64 encoded characters into the original byte array of binary data.
     * 
     * @param bytes A byte array representing Base64 encoded characters.
     * @return The original byte array of binary data.
     * @throws IOException if an error occurs during decoding.
     */
    private static final byte[] decodeBytes(final byte[] bytes) throws IOException {
        final java.io.InputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes));
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final java.io.OutputStream os = new BufferedOutputStream(baos);
        final byte[] inputBuffer = new byte[DECODE_BLOCK_SIZE_BYTES];
        final byte[] outputBuffer = new byte[ENCODE_BLOCK_SIZE_BYTES];
        byte[] decodedBytes = null;
        int numberRead = 0;

        try {
            // Continue to read bytes from the stream until none are available.
            while (numberRead != EOS) {
                numberRead = decodeBlock(is, inputBuffer, outputBuffer);
                if (numberRead != EOS) {
                    os.write(outputBuffer, 0, numberRead);
                }
            }
            os.flush();
            decodedBytes = baos.toByteArray();
        } finally {
            is.close();
            os.close();
        }

        return decodedBytes;
    }

    /**
     * Encodes the supplied byte data into a byte array of Base64 encoded data.
     * 
     * @param bytes Binary data to be encoded into Base64.
     * @return A byte array containing Base64 encoded data.
     * @throws IOException if an error occurs during encoding.
     */
    private static final byte[] encodeBytes(final byte[] bytes) throws IOException {
        final java.io.InputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes));
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final java.io.OutputStream os = new BufferedOutputStream(baos);
        byte[] inputBuffer = new byte[ENCODE_BLOCK_SIZE_BYTES];
        byte[] outputBuffer = new byte[DECODE_BLOCK_SIZE_BYTES];
        byte[] encodedBytes = null;
        int numberRead = 0;

        try {
            // Continue to read bytes from the stream until none are available.
            while (numberRead != EOS) {
                numberRead = encodeBlock(is, inputBuffer, outputBuffer);
                if (numberRead != EOS) {
                    os.write(outputBuffer);
                }
            }
            os.flush();
            encodedBytes = baos.toByteArray();
        } finally {
            is.close();
            os.close();
        }

        return encodedBytes;
    }

    /**
     * Encodes the next block of data read from the supplied input stream and writes the encoded value to the supplied output
     * stream.
     * 
     * @param is The source of the data to be encoded.
     * @param inputBuffer The buffer containing the data to be encoded.
     * @param outputBuffer The buffer where the encoded data will be placed.
     * @return The number of bytes read or -1 if EOS is encountered.
     * @throws IOException if an error occurs while attempting to read, or encode the data.
     */
    private static int encodeBlock(final java.io.InputStream is, final byte[] inputBuffer, final byte[] outputBuffer)
            throws IOException {
        /*
         * Read one block (3 bytes) of data at a time and encode it as base 64 data.
         */
        int numBytesRead = readMostly(is, inputBuffer);

        /*
         * Check for EOS.
         */
        if (numBytesRead != EOS) {
            encodeData(inputBuffer, outputBuffer, numBytesRead);
        }

        return numBytesRead;
    }

    /**
     * Encodes a block of byte data into base 64 encoded data.
     * 
     * @param inputBuffer The buffer containing the data to be encoded.
     * @param outputBuffer The buffer where the encoded data will be placed.
     * @param numberOfBytes The number of bytes read during the encoding process for this block. This value is important to
     *        determine if special padding is required.
     * @throws IOException if an error occurs while attempting to encode the data.
     */
    private static void encodeData(final byte[] inputBuffer, final byte[] outputBuffer, final int numberOfBytes)
            throws IOException {
        int convertedIndex = 0;
        int currentIndex = 0;

        if (inputBuffer != null) {
            while (currentIndex < inputBuffer.length && convertedIndex < DECODE_BLOCK_SIZE_BYTES
                    && currentIndex < numberOfBytes) {
                switch (convertedIndex) {
                    case 0:
                        // Left 6 bits of the first byte
                        outputBuffer[convertedIndex] = ((byte) BASE_64_CHARACTERS
                                .codePointAt(((inputBuffer[currentIndex] >> 2) & 0x3f)));
                        break;
                    case 1:
                        // Right 2 bits of first byte OR'ed with left 4 bits of second byte
                        byte secondByte = (byte) ((inputBuffer[currentIndex] & 0x3) << 4);
                        currentIndex++;
                        outputBuffer[convertedIndex] = ((byte) BASE_64_CHARACTERS
                                .codePointAt((secondByte | (currentIndex == numberOfBytes ? 0 : ((inputBuffer[currentIndex] & 0xf0) >> 4)))));
                        break;
                    case 2:
                        // Right 4 bits of second byte OR'ed with left 2 bits of third byte
                        byte thirdByte = (byte) ((inputBuffer[currentIndex] & 0xf) << 2);
                        currentIndex++;
                        outputBuffer[convertedIndex] = ((byte) BASE_64_CHARACTERS
                                .codePointAt((thirdByte | (currentIndex == numberOfBytes ? 0 : ((inputBuffer[currentIndex] & 0xc0) >> 6)))));
                        break;
                    case 3:
                        // Right 6 bits of the third byte
                        outputBuffer[convertedIndex] = ((byte) BASE_64_CHARACTERS
                                .codePointAt((inputBuffer[currentIndex]) & 0x3f));
                        currentIndex++;
                        break;
                }
                convertedIndex++;
            }
        }

        // If padding is required, add the padding character to the output for this block.
        for (int i = convertedIndex; i < DECODE_BLOCK_SIZE_BYTES; i++) {
            outputBuffer[i] = (byte) PADDING_CHARACTER;
        }
    }

    /**
     * Decodes the next block of data read from the supplied input stream and writes the decoded value to the supplied output
     * stream.
     * 
     * @param is The source of the data to be decoded.
     * @param inputBuffer The input buffer that contains base 64 encoded data.
     * @param outputBuffer The output buffer where the decoded data will be placed.
     * @return The number of bytes read or -1 if EOS is encountered.
     * @throws IOException if an error occurs while attempting to read, or decode the data.
     */
    private static int decodeBlock(final java.io.InputStream is, final byte[] inputBuffer, final byte[] outputBuffer)
            throws IOException {
        /*
         * Read one block (4 bytes) of Base 64 encoded data at a time and decode to the original byte values, removing any
         * padding present if necessary.
         */
        int numberRead = readMostly(is, inputBuffer);

        /*
         * Check for EOS.
         */
        if (numberRead != EOS) {
            for (int i = 0; i < DECODE_BLOCK_SIZE_BYTES; i++) {
                /*
                 * Note that the padding character ('='), will cause a value of -1 to be put into the byte array. This will
                 * be detected later on by the decoding process and recognized as a padding byte and will be removed from
                 * the decoded value.
                 */
                inputBuffer[i] = (byte) BASE_64_CHARACTERS.indexOf((char) inputBuffer[i]);
            }
            return decodeData(inputBuffer, outputBuffer);
        }

        return EOS;
    }

    /**
     * Decodes a block of base 64 encoded data.
     *
     * @param inputBuffer The buffer containing the data to be decoded.
     * @param outputBuffer The buffer where the decoded data will be placed.
     * @return number of bytes decoded
     * @throws IOException if an error happens during decoding of the data.
     */
    private static int decodeData(final byte[] inputBuffer, final byte[] outputBuffer) throws IOException {
        int currentIndex = 0;
        int shiftAmount = 2;
        int mask = 0x3;
        int actualLength = findOriginalBlockSize(inputBuffer);

        if (inputBuffer != null) {
            while (currentIndex < actualLength) {
                byte currentByte = (byte) (inputBuffer[currentIndex] << shiftAmount);
                switch (currentIndex) {
                    case 0:
                        outputBuffer[currentIndex] = ((byte) (currentByte | ((inputBuffer[currentIndex + 1] >> (shiftAmount + 2)) & mask)));
                        break;
                    case 1:
                        outputBuffer[currentIndex] = ((byte) (currentByte | ((inputBuffer[currentIndex + 1] >> (shiftAmount - 2)) & mask)));
                        break;
                    case 2:
                        outputBuffer[currentIndex] = ((byte) (currentByte | (inputBuffer[currentIndex + 1] & mask)));
                        break;
                }

                shiftAmount += 2;
                mask = ((mask << 2) | 0x3);
                currentIndex++;
            }

            return actualLength;
        }

        return 0;
    }

    /**
     * Determines how many bytes were originally used to encode the current block of four (4) encoded bytes. Specifically, this
     * method determines if padding was added to the encoded block. If padding was added, this means that the original input
     * block for encoding was less than three (3) bytes.
     * 
     * @param block A block of four (4) base 64 encoded bytes.
     * @return The original number of bytes used by the algorithm to produce the encoded block of four (4) bytes.
     */
    private static int findOriginalBlockSize(final byte[] block) {
        int length = ENCODE_BLOCK_SIZE_BYTES;

        if (block[3] == PADDING_VALUE) {
            length--;
        }

        if (block[2] == PADDING_VALUE) {
            length--;
        }

        return length;
    }

    /**
     * Base 64 input stream that supports both the encoding and decoding of the wrapped input stream.
     */
    public static final class InputStream extends FilterInputStream {

        /**
         * The operation to be performed on the data in the stream.
         */
        private final Operations operation;

        /**
         * Temporary input buffer to be used to read data from the underlying stream.
         */
        private byte[] inputBuffer;

        /**
         * Temporary output buffer used to store intermediate results.
         */
        private byte[] outputBuffer;

        /**
         * The current position within the temporary buffer. This is used to keep track of which byte to "read" next.
         */
        private int position = EOS;

        private int available = 0;

        /**
         * Creates a new base 64 input stream wrapped around the supplied java.io.InputStream instance.
         * 
         * @param is A java.io.InputStream stream of data.
         */
        public InputStream(final java.io.InputStream is) {
            this(is, Operations.DECODE);
        }

        /**
         * Creates a new base 64 input stream wrapped around the supplied java.io.InputStream instance that performs the
         * requested operation on the data.
         * 
         * @param is A java.io.InputStream stream of data.
         * @param operation The operation to be performed on the data.
         */
        public InputStream(final java.io.InputStream is, final Operations operation) {
            super(is);
            this.operation = operation;
            inputBuffer = new byte[(operation == Operations.ENCODE ? ENCODE_BLOCK_SIZE_BYTES : DECODE_BLOCK_SIZE_BYTES)];
            outputBuffer = new byte[(operation == Operations.ENCODE ? DECODE_BLOCK_SIZE_BYTES : ENCODE_BLOCK_SIZE_BYTES)];
        }

        @Override
        public int read() throws IOException {
            /*
             * If there is no data waiting to be processed in the temporary buffer, it's time to go get more data.
             */

            if (position < 0) {
                if (operation == Operations.ENCODE) {
                    encodeBlock(in, inputBuffer, outputBuffer);
                    available = outputBuffer.length;
                } else {
                    available = decodeBlock(in, inputBuffer, outputBuffer);
                }

                /*
                 * If actual data was processed, reset the position pointer to 0. Otherwise, leave it as -1 to let the EOS
                 * propagate.
                 */
                if (available != EOS) {
                    position = 0;
                }
            }

            /*
             * If the position pointer is somehow beyond the length of the buffer or has been set to -1, return EOS. Otherwise,
             * read the next byte from the temporary buffer.
             */
            if (position >= available || position < 0) {
                return EOS;
            } else {
                final byte b = outputBuffer[position];
                position++;
                if (position >= available) {
                    position = EOS;
                }

                // Mask with 0xFF to convert to unsigned
                return b & 0xFF;
            }
        }

        @Override
        public int read(byte[] b) throws IOException {
            return read(b, 0, b.length);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int i = 0;

            for (i = 0; i < len; i++) {
                final int value = read();
                if (value >= 0) {
                    b[off + i] = (byte) value;
                } else if (i == 0) {
                    return EOS;
                } else {
                    break;
                }
            }

            return i;
        }
    }

    /**
     * Base 64 input stream that supports both the encoding and decoding of the wrapped input stream.
     */
    public static final class OutputStream extends FilterOutputStream {

        /**
         * The operation to be performed on the data in the stream.
         */
        private final Operations operation;

        /**
         * Temporary buffer used to store intermediate input data.
         */
        private byte[] inputBuffer;

        /**
         * Temporary buffer used to store intermediate output data.
         */
        private byte[] outputBuffer;

        /**
         * The current position within the temporary buffer. This is used to determine if enough bytes have been accumulated to
         * be written.
         */
        private int position = 0;

        /**
         * Creates a new base 64 output stream wrapped around the supplied java.io.OutputStream instance.
         * 
         * @param os A java.io.OutputStream stream of data.
         */
        public OutputStream(final java.io.OutputStream os) {
            this(os, Operations.ENCODE);
        }

        /**
         * Creates a new base 64 output stream wrapped around the supplied java.io.OutputStream instance.
         * 
         * @param os A java.io.OutputStream stream of data.
         * @param operation The operation to be performed on the data.
         */
        public OutputStream(final java.io.OutputStream os, final Operations operation) {
            super(os);
            this.operation = operation;
            inputBuffer = new byte[(operation == Operations.ENCODE ? ENCODE_BLOCK_SIZE_BYTES : DECODE_BLOCK_SIZE_BYTES)];
            outputBuffer = new byte[(operation == Operations.ENCODE ? DECODE_BLOCK_SIZE_BYTES : ENCODE_BLOCK_SIZE_BYTES)];
        }

        @Override
        public void write(final int b) throws IOException {
            int numberToWrite = 0;
            inputBuffer[position] = (byte) b;
            position++;
            if (position >= inputBuffer.length) {
                if (operation == Operations.ENCODE) {
                    encodeData(inputBuffer, outputBuffer, inputBuffer.length);
                    numberToWrite = DECODE_BLOCK_SIZE_BYTES;
                } else {
                    numberToWrite = decodeData(inputBuffer, outputBuffer);
                }
                out.write(outputBuffer, 0, numberToWrite);
                position = 0;
            }
        }

        @Override
        public void write(byte[] b) throws IOException {
            write(b, 0, b.length);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            for (int i = 0; i < len; i++) {
                write(b[off + i]);
            }
        }

        /**
         * Closes this stream by writing any pending data to the underlying stream. NOTE: THIS METHOD DOES NOT CLOSE THE
         * UNDERLYING STREAM. You must close the underlying stream manually.
         */
        public void close() throws IOException {
            finish();
        }

        /**
         * Performs any final clean-up/writing to ensure ALL data has been output to the wrapped output stream. This method
         * handles edge cases in the base 64 specification, such as padding, etc.
         * 
         * @throws IOException if an error occurs during final output.
         */
        public void finish() throws IOException {
            /*
             * This block is required to catch the case where the last block written to the stream to be encoded is not 3 bytes
             * (i.e., it requires padding). This block will catch that case and flush the remaining bytes to ensure ALL data is
             * written out when the operation is ENCODE.
             */
            if (operation == Operations.ENCODE && position < ENCODE_BLOCK_SIZE_BYTES) {
                // Flush the remaining bytes!
                encodeData(inputBuffer, outputBuffer, position);
                out.write(outputBuffer);
            }
        }
    }

    /**
     * Enumeration representing the operations that can be performed by the input and output streams.
     */
    public static enum Operations {
        DECODE, ENCODE;
    }
}
